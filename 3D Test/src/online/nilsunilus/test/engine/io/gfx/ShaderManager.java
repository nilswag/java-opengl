package online.nilsunilus.test.engine.io.gfx;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.nio.FloatBuffer;
import java.util.HashMap;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL33;

import online.nilsunilus.test.engine.io.gfx.Renderer.BufferWrapper;
import online.nilsunilus.test.engine.utility.Logger;

/*

int program_id = GL33.glCreateProgram();

int vertex_shader_id = GL33.glCreateShader(GL33.GL_VERTEX_SHADER);
GL33.glShaderSource(vertex_shader_id, vertex_src);
GL33.glCompileShader(vertex_shader_id);
GL33.glAttachShader(program_id, vertex_shader_id);

GL33.glLinkProgram(program_id);
GL33.glValidateProgram(program_id);
if(GL33.glGetProgrami(program_id, GL33.VALIDATE_STATUS) == 0)
	Logger.log("Error in shaderprogram: " + GL33.glGetProgramInfo(program_id, 1024) + "\n", System.err);
	
GL33.glUseProgram(program_id);

Shader workflow:
1. Create a OpenGL Program
2. Load the vertex and fragment shader code files.
3. For each shader, create a new shader program and specify its type (vertex, fragment).
4. Compile the shader.
5. Attach the shader to the program.
6. Link the program.
7. Memory management
	 
*/

public class ShaderManager {
	
	public class Shader {
		private int program_id;
		private int vertex_shader_id, fragment_shader_id;
		
		public Shader(int program_id, int vertex_shader_id, int fragment_shader_id) {
			this.program_id = program_id;
			this.vertex_shader_id = vertex_shader_id;
			this.fragment_shader_id = fragment_shader_id;
		}
		
		public void init() {
			
		}
		
		public void loadUniform(int location, Object type)
		{
			if(type instanceof Vector3f) {
				Vector3f type_ = (Vector3f)type;
				GL33.glUniform3f(location, type_.x, type_.y, type_.z);
			} else if(type instanceof Float) {
				GL33.glUniform1f(location, (float)type);
			} else if(type instanceof Matrix4f) {
				FloatBuffer matrix_buffer = BufferUtils.createFloatBuffer(16);
				Matrix4f type_ = (Matrix4f)type;
				type_.get(matrix_buffer);
				matrix_buffer.flip();
				GL33.glUniformMatrix4fv(location, false, matrix_buffer);
			}
		}
		
		public int getUniformLocation() {
			return 0;
		}
		
		public void start() {
			GL33.glUseProgram(program_id);
		}
		
		public void stop() {
			GL33.glUseProgram(0);
		}
		
		public void clean() {
			stop();
			GL33.glDetachShader(program_id, vertex_shader_id);
			GL33.glDetachShader(program_id, fragment_shader_id);
			GL33.glDeleteShader(vertex_shader_id);
			GL33.glDeleteShader(fragment_shader_id);
			GL33.glDeleteProgram(program_id);
		}

		public int getProgram_id() {
			return program_id;
		}

		public int getVertex_shader_id() {
			return vertex_shader_id;
		}

		public int getFragment_shader_id() {
			return fragment_shader_id;
		}
		
	}
	
	private HashMap<String, Shader> shader_map;
	
	public void init() {
		shader_map = new HashMap<>();
	}
	
	public void initShader(String key, String vertex_path, String fragment_path) {
		int program_id = GL33.glCreateProgram();
		int vertex_id = loadTypeShader(vertex_path, GL33.GL_VERTEX_SHADER, program_id);
		int fragment_id = loadTypeShader(fragment_path, GL33.GL_FRAGMENT_SHADER, program_id);
		Shader shader = new Shader(program_id, vertex_id, fragment_id);
		shader.init();
		shader_map.put(key, shader);
	}
	
	public void bindShader(Shader shader, HashMap<Integer, BufferWrapper> buffer_data_map) {
		buffer_data_map.keySet().forEach(i -> GL33.glBindAttribLocation(shader.getProgram_id(), i, buffer_data_map.get(i).getVar_name()));
	}
	
	private int loadTypeShader(String local_path, int type, int program_id) {
		StringBuilder builder = new StringBuilder();
		try {
			BufferedReader buffered_reader = new BufferedReader(new FileReader(new File("assets/shaders/" + local_path)));
			buffered_reader.lines().forEach(i -> builder.append(i + "\n"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		String src = builder.toString();
		int shader_id = GL33.glCreateShader(type);
		GL33.glShaderSource(shader_id, src);
		GL33.glCompileShader(shader_id);
		
		if(GL33.glGetShaderi(shader_id, GL33.GL_COMPILE_STATUS) == GL33.GL_FALSE) 
			Logger.log("Shader error (" + local_path + "): " + GL33.glGetShaderInfoLog(shader_id, 1024), System.err);
		
		GL33.glAttachShader(program_id, shader_id);
		GL33.glValidateProgram(program_id);
		GL33.glLinkProgram(program_id);
		return shader_id;
	}
	
	public void clean() {
		shader_map.values().forEach(Shader::clean);
	}
	
	public Shader getShader(String key) {
		if(!shader_map.containsKey(key))
			Logger.log(key + " has not been set.", System.err);
		return shader_map.get(key);
	}

}
