package online.nilsunilus.test.game.main;

import java.util.HashMap;

import org.joml.Matrix4f;

import online.nilsunilus.test.engine.core.Game;
import online.nilsunilus.test.engine.core.GameContainer;
import online.nilsunilus.test.engine.io.gfx.Mesh;
import online.nilsunilus.test.engine.io.gfx.Renderer;
import online.nilsunilus.test.engine.io.gfx.Renderer.BufferWrapper;
import online.nilsunilus.test.engine.io.gfx.ShaderManager.Shader;

@SuppressWarnings("unused")
public class Test extends Game {
	
	private float[] vertices;
	private int[] indices;
	private float[] colors;
	private Mesh mesh;
	private Shader static_shader;
	
	public Test(GameContainer game_container) {
		super(game_container);
	}
	
	@Override
	public void init() {
		vertices = new float[] {
			-0.5f, -0.5f, 0.0f,
			0.0f, 0.5f, 0.0f,
			0.5f, -0.5f, 0.0f
		};
		
		indices = new int[] {
			0, 1, 2
		};
		
		colors = new float[] {
			1.0f, 0.0f, 0.0f,
			0.0f, 1.0f, 0.0f,
			0.0f, 0.0f, 1.0f
		};
		
		HashMap<Integer, BufferWrapper> buffer_data_map = new HashMap<>();
		buffer_data_map.put(0, new BufferWrapper(vertices, 3, "position"));
		buffer_data_map.put(1, new BufferWrapper(colors, 3, "color"));
		mesh = Renderer.loadMesh(buffer_data_map, indices);
		
		game_container.getShaderManager().initShader("static_shader", "static/vertex.glsl", "static/fragment.glsl");
		static_shader = game_container.getShaderManager().getShader("static_shader");
		game_container.getShaderManager().bindShader(static_shader, buffer_data_map);
 	} 
		
	@Override
	public void render(Renderer renderer) {
		static_shader.start();
		Renderer.renderMesh(mesh);
		static_shader.stop();
	}

}
