package io.socket.client;

import io.socket.emitter.Emitter;

import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONArray;

import com.gt.util.PropertiesUtil;



@SuppressWarnings({ "rawtypes", "unchecked" })
public class socke {
	static Socket socket;

	public static void socketConnet() {
		try {
			String host = PropertiesUtil.getSocketUrl();
			socket = IO.socket(host);
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {

			@Override
			public void call(Object... args) {
				/*
				 * Map map = new HashMap(); map.put("userName", "admin");
				 * map.put("message", "链接成功"); socket.emit("chatevent", map);
				 */
			}

		}).on("chatevent", new Emitter.Listener() {

			@Override
			public void call(Object... args) {
				System.out.println(args[0]);
			}

		}).on(Socket.EVENT_DISCONNECT, new Emitter.Listener() {

			@Override
			public void call(Object... args) {
			}

		});
		socket.connect();
	}

	public static void sendMessage(Integer bus_user_id, Object message) {
		Map map = new HashMap();
		map.put("userId", bus_user_id);
		map.put("message", message);
		socket.emit("chatevent", map);
	}

	public static void sendMessage2(String identifier, Object message) {
		Map map = new HashMap();
		map.put("userId", identifier);
		map.put("message", message.toString());
		socket.emit("chatevent", map);
	}

	public static void main(String arg[]) {
		socke.socketConnet();// 连接socket
		JSONArray jsonArray = new JSONArray();
		for(int i=0;i<100;i++){
			Map map = new HashMap();
			map.put("w", "0");
			map.put("da", "di");
			jsonArray.add(map);
		}
		
		sendMessage2("DPM413", jsonArray.toString());
	}
}
