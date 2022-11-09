package online.nilsunilus.test.engine.io.gfx;

import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL33;

import online.nilsunilus.test.engine.core.Game;
import online.nilsunilus.test.engine.io.WindowManager;

public class Renderer {
	
	private static List<Integer> vao_ids = new ArrayList<>();
	private static List<Integer> bo_ids = new ArrayList<>();
	
	public void render(WindowManager window_manager, Game game) {
		GL33.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
		GL33.glClear(GL33.GL_COLOR_BUFFER_BIT);
		
		game.render(this);
		
		glfwSwapBuffers(window_manager.getGlfwWindow());
	}
	
	public static class BufferWrapper {
		
		private float[] data;
		private int count;
		private String var_name;
		
		public BufferWrapper(float[] data, int count, String var_name) {
			this.data = data;
			this.count = count;
			this.var_name = var_name;
		}
		
		public float[] getData() {
			return data;
		}
		
		public int getCount() {
			return count;
		}
		
		public String getVar_name() {
			return var_name;
		}
		
	}
	
	public static Mesh loadMesh(HashMap<Integer, BufferWrapper> buffer_data_map, int[] indices) {
		int vao_id = GL33.glGenVertexArrays();
		vao_ids.add(vao_id);
		GL33.glBindVertexArray(vao_id);
		
		int ibo_id = GL33.glGenBuffers();
		bo_ids.add(ibo_id);
		GL33.glBindBuffer(GL33.GL_ELEMENT_ARRAY_BUFFER, ibo_id);
		IntBuffer index_buffer = BufferUtils.createIntBuffer(indices.length);
		index_buffer.put(indices);
		index_buffer.flip();
		GL33.glBufferData(GL33.GL_ELEMENT_ARRAY_BUFFER, index_buffer, GL33.GL_STATIC_DRAW);
		
		buffer_data_map.keySet().forEach(i -> setBufferObject(
			buffer_data_map.get(i).getData(), 
			buffer_data_map.get(i).getCount(), 
			i
		));
		
		GL33.glBindVertexArray(0);
		return new Mesh(vao_id, indices.length, buffer_data_map);
	}
	
	private static void setBufferObject(float[] data, int count, int attribute_index) {
		int bo_id = GL33.glGenBuffers();
		bo_ids.add(bo_id);
		GL33.glBindBuffer(GL33.GL_ARRAY_BUFFER, bo_id);
		
		FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
		buffer.put(data);
		buffer.flip();
		
		GL33.glBufferData(GL33.GL_ARRAY_BUFFER, buffer, GL33.GL_STATIC_DRAW);
		GL33.glVertexAttribPointer(attribute_index, count, GL33.GL_FLOAT, false, 0, 0);
	}
	
	public static void renderMesh(Mesh mesh) {
		GL33.glBindVertexArray(mesh.getVao_id());
		mesh.getBuffer_data_map().keySet().forEach(i -> GL33.glEnableVertexAttribArray(i));
		GL33.glDrawElements(GL33.GL_TRIANGLES, mesh.getVertex_count(), GL33.GL_UNSIGNED_INT, 0);
		mesh.getBuffer_data_map().keySet().forEach(i -> GL33.glDisableVertexAttribArray(i));
		GL33.glBindVertexArray(0);
	}

	public static void clean() {
		vao_ids.forEach(i -> GL33.glDeleteVertexArrays(i));
		bo_ids.forEach(i -> GL33.glDeleteBuffers(i));
	}

}
