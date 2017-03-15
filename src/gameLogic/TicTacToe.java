package gameLogic;

public class TicTacToe {
    private char gameGrid[][] = {{' ', ' ', ' '}, {' ', ' ', ' '}, {' ', ' ', ' '}};
    private int width = 0, height = 0, turn = 0;
    private boolean game_finished = false;
    private char last_player = ' ';
    private int playerXWins = 0, playerOWins = 0, draws = 0;

    public TicTacToe(){}

    public char[][] getGameGrid() {
        return gameGrid;
    }
    public void setGameGrid(char[][] gameGrid) {
        this.gameGrid = gameGrid;
    }
    public int getWidth() {
        return width;
    }
    public int getHeight() {
        return height;
    }
    public void setWidth(int width) {
        this.width = width;
    }
    public void setHeight(int height) {
        this.height = height;
    }
    public int getTurn() {
        return turn;
    }
    public void setTurn(int turn) {
        this.turn = turn;
    }
    public boolean isGame_finished() {
        return game_finished;
    }
    public void setGame_finished(boolean game_finished) {
        this.game_finished = game_finished;
    }
    public char getLast_player() {
        return last_player;
    }
    public void setLast_player(char last_player) {
        this.last_player = last_player;
    }
    public int getPlayerXWins() {
        return playerXWins;
    }
    public void setPlayerXWins(int playerXWins) {
        this.playerXWins = playerXWins;
    }
    public int getPlayerOWins() {
        return playerOWins;
    }
    public void setPlayerOWins(int playerOWins) {
        this.playerOWins = playerOWins;
    }
    public int getDraws() {
        return draws;
    }
    public void setDraws(int draws) {
        this.draws = draws;
    }
}
