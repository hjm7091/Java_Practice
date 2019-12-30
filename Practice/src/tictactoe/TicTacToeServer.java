package tictactoe;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class TicTacToeServer {

	public static void main(String[] args) throws Exception {
		ServerSocket ss = new ServerSocket(9001);
		System.out.println("Tic Tac Toe 서버가 시작되었습니다.");
		int number = 0;
		try {
			while(true) {
				number++;
				Game game = new Game(number);
				
				Player player1 = new Player(game, ss.accept(), 'X');
				Player player2 = new Player(game, ss.accept(), 'O');
				
				player1.setOther(player2);
				player2.setOther(player1);
				
				player1.start();
				player2.start();
				System.out.println("페어가 만들어 졌습니다. ");
			}
		}finally {
			ss.close();
		}
	}

}

class Game{
	int gameNum;
	char[][] board = new char[3][3];
	
	public Game(int number) {
		gameNum = number;
	}
	
	public void setBoard(int i, int j, char playerMark) {
		board[i][j] = playerMark;
	}
	
	public char getBoard(int i, int j) {
		return board[i][j];
	}
	
	public char[][] getAllBoard(){
		return board;
	}
	
	public int getGameNum() {
		return gameNum;
	}
	
	public void printBoard() {
		System.out.println("GameNum : " + gameNum);
		for(int k=0; k<3; k++) {
			System.out.println("  " + board[k][0] + "|  " + board[k][1] + "|  " + board[k][2]);
			if(k!=2)
				System.out.println("---|---|---");
		}
		System.out.println();
	}
}

class Player extends Thread{
	
	Game game;
	Socket socket;
	BufferedReader input;
	PrintWriter output;
	char playerMark;
	Player other;
	
	public Player(Game game, Socket socket, char playerMark) {
		this.game = game;
		this.socket = socket;
		this.playerMark = playerMark;
		try {
			input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			output = new PrintWriter(socket.getOutputStream(), true);
			output.println("START " + playerMark);
			output.println("PRINT 다른 경기자를 기다립니다.");
		} catch (IOException e) {
			System.out.println("연결이 끊어졌습니다. " + e);
		}
	}
	
	public void setOther(Player other) {
		this.other = other;
	}
	
	public void run() {
		try {
			output.println("PRINT 모든 경기자가 연결되었습니다.");
			if(playerMark == 'X')
				output.println("PRINT 당신 차례입니다.");
			
			while(true) {
				String command = input.readLine();
				if(command==null)
					continue;
				if(command.startsWith("MOVE")) {
					int i = Integer.parseInt(command.substring(5, 6));
					int j = Integer.parseInt(command.substring(7, 8));
					game.setBoard(i, j, playerMark);
					game.printBoard();
					if(checkWinner(i, j)) {
						output.println("WIN");
						other.output.println("LOSE");
						return;
					}
					other.output.println("OTHER " + i + " " + j);
					output.println("PRINT 기다리세요!");
					other.output.println("PRINT 당신 차례입니다.");
				}
				else if(command.startsWith("QUIT")) {
					return;
				}
			}
			
		} catch (IOException e) {
			System.out.println("GameNum : " + game.getGameNum());
			System.out.println("사용자 : " + playerMark + " 종료");
		} finally {
			try {
				socket.close();
			}catch (IOException e) {
				System.out.println("소켓 닫기 오류");
			}
		}
	}
	
	public boolean checkWinner(int x, int y) {
		char[][] board = game.getAllBoard();
		int N = board.length;
		char now = game.getBoard(x, y);
		if(checkCol(x, y, now, N) || checkRow(x, y, now, N))
			return true;
		return false;
	}
	
	public boolean checkCol(int x, int y, char now, int N) {
		int cnt = 1;
		for(int ny=0; ny<y; ny++) {
			if(game.getBoard(x, ny) == now)
				cnt++;
		}
		for(int ny=y+1; ny<N; ny++) {
			if(game.getBoard(x, ny) == now)
				cnt++;
		}
		if(cnt==N)
			return true;
		else
			return false;
	}
	
	public boolean checkRow(int x, int y, char now, int N) {
		int cnt = 1;
		for(int nx=0; nx<x; nx++) {
			if(game.getBoard(nx, y) == now)
				cnt++;
		}
		for(int nx=x+1; nx<N; nx++) {
			if(game.getBoard(nx, y) == now)
				cnt++;
		}
		if(cnt==N)
			return true;
		else
			return false;
	}
}
