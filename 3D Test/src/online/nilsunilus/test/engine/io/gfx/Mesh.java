package online.nilsunilus.test.engine.io.gfx;

import java.util.HashMap;

public class Mesh {
	
	private int vao_id, vertex_count;
	private HashMap<Integer, Renderer.BufferWrapper> buffer_data_map;

	public Mesh(int vao_id, int vertex_count, HashMap<Integer, Renderer.BufferWrapper> buffer_data_map) {
		this.vao_id = vao_id;
		this.vertex_count = vertex_count;
		this.buffer_data_map = buffer_data_map;
	}

	public int getVao_id() {
		return vao_id;
	}

	public int getVertex_count() {
		return vertex_count;
	}

	public HashMap<Integer, Renderer.BufferWrapper> getBuffer_data_map() {
		return buffer_data_map;
	}

}
