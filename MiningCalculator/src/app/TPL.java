package app;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

public class TPL {

	public static void main (String args[]) {
		/*
		comparator = new Comparator<Player>() {
			@Override
			public int compare(Player o1, Player o2) {
				int diff1, diff2, diff3;

				diff1 = o2.score_1 - o1.score_1;
				if (diff1 != 0) return diff1;
				diff2 = o2.score_2 - o1.score_2;
				if (diff2 != 0) return diff2;
				diff3 = o2.score_3 - o1.score_3;
				if (diff3 != 0) return diff3;
				return 0;
			}
		};
		
		Player player_01 = new Player(10, 20, 30, "player_01");
		topPlayersResult[0] = player_01;
		
		}*/
		
		List<Player> unorderedList = new ArrayList<>();
		
		Player player_01 = new Player(10, "player_01");
		unorderedList.add(player_01);
		
		Player player_03 = new Player(70, "player_03");
		unorderedList.add(player_03);
		
		Player player_02 = new Player(80, "player_02");
		unorderedList.add(player_02);
		
		
		PriorityQueue<Player> heap = new PriorityQueue<>(unorderedList.size());
		heap.addAll(unorderedList);
		
		List<Player> topElements = new ArrayList<>();
		int top = 3;
		for (int i = 0; i < top; i++) {
			topElements.add(heap.poll());
		}
		
		for (Player player : topElements) {
			System.out.println(player);
		}
		
	}
}
