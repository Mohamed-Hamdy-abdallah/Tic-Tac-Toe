package clientHandler;

import clientside.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class ClientHandler {

    private static Socket clientSocket;
    private static DataInputStream ds;
    private static Stage window;
    private static Player player;
    private static String currentScene;
    private static DataOutputStream ps;
    private static LoginFXMLController loginctrl;
    private static StartFXMLController startctrl;
    private static NewgameFXMLController newgamectrl;
    private static LoadgameFXMLController loadgamectrl;
    private static InviteFXMLController Invitectrl;
    private static PlayingModeFXMLController Playmodectrl;
    private static InvitationFXMLController invitationCtrl;
    private static MultigameFXMLController multigameCtrl;
    private static ObservableList<String> name = FXCollections.observableArrayList();
    private static ObservableList<String> status = FXCollections.observableArrayList();
    private static ObservableList<String> score = FXCollections.observableArrayList();
    private static ObservableList<String> games = FXCollections.observableArrayList();
    private static String invitingUsername;
    private static boolean gameAccepted = false;
    private static boolean replay = false;
    private static JSONArray gamesFullInfo;
    private static char[][] gameBoard = new char[3][3];
    private static char nextMove;
    private static boolean isLoaded = false;
    private static String nextPlayer;
    private static boolean clientDropped = false;
    private static boolean isConnected;

    // Creating and starting Connection
    public static boolean connectToServer() {
        boolean res = true;
        try {
            clientSocket = new Socket("127.0.0.1", 7771);
            ds = new DataInputStream(clientSocket.getInputStream());
            ps = new DataOutputStream(clientSocket.getOutputStream());

        } catch (IOException ex) {
            System.out.println("Connection failed");
            res = false;
        }
        return res;
    }

    // Closing Connection
    public static void closeCon() {
        try {
            ps.close();
            ds.close();
            clientSocket.close();
        } catch (IOException ex) {
            System.out.println("Connection closing failed");
        }
    }

    // Sending request to the server for Multi or Signing out
    public static void sendRequest(JSONObject jsonMsg) {
        try {
            ps.writeUTF(jsonMsg.toJSONString());
        } catch (IOException ex) {
            System.out.println("Error sending request to the server");
        }
    }

    // Receive requests from the server
    public static class recieveRespone implements Runnable {

        boolean running = true;
        String response;

        @Override
        public void run() {
            while (running) {
                try {
                    response = ds.readUTF();
                    if (response != null) {
                        handleResponse(response);
                    }
                } catch (IOException ex) {
                    running = false;
                    changeScene("Confailed");
                }
            }
        }
    }

    // Server request handler
    private static void handleResponse(String response) {
        try {
            JSONParser parser = new JSONParser();
            JSONObject jsonMsg = (JSONObject) parser.parse(response);
            switch (jsonMsg.get("type").toString()) {
                case "signin":
                    Login(jsonMsg);
                    break;
                case "signup":
                    Login(jsonMsg);
                    break;
                case "updateList":
                    updateList(jsonMsg);
                    break;
                case "invitePlayer":
                    invitePlayerResponse(jsonMsg);
                    break;
                case "invitation":
                    invitationRequest(jsonMsg);
                    break;
                case "sendMove":
                    getMoveReponse(jsonMsg);
                    break;
                case "gameStarted":
                    GameStartedResponse(jsonMsg);
                    break;
                case "gameEnded":
                    gameEndedResponse(jsonMsg);
                    break;
                case "getGames":
                    displayGamesList(jsonMsg);
                    break;
                case "loadGame":
                    loadGameResponse(jsonMsg);
                    break;
                case "invitationResponse":
                    getInvitationResponse(jsonMsg);
                    break;
                case "saveGame":
                    saveGameResponse(jsonMsg);
                    break;
                case "gameQuit":
                    gameQuitResponse(jsonMsg);
                    break;

            }
        } catch (ParseException ex) {
            System.out.println("Unknown request from the server");
        }
    }

    // Changing scence
    @FXML
    public static void changeScene(String newScene) {
        setCurrentScene(newScene);
        Platform.runLater(() -> {
            try {
                Parent root = FXMLLoader.load(ClientSide.class.getResource(newScene + "FXML.fxml"));
                Scene scene = new Scene(root);
                window.setScene(scene);
                window.setResizable(false);
                window.show();
            } catch (IOException ex) {
                System.out.println("Can't chaning the scene");
            }
        });
    }

    // Setters and getters for the scene
    public static void setWindow(Stage stage) {
        window = stage;
    }

    public static Stage getWindow() {
        return window;
    }

    public static void setCurrentScene(String scene) {
        currentScene = scene;
    }

    public static String getCurrentScene() {
        return currentScene;
    }

    // Setter and getter for the player
    public static void setPlayer(Player person) {
        player = person;
    }

    public static Player getPlayer() {
        return player;
    }

    // setters and getters for the game scences according to the player
    public static void setLoginCtrl(LoginFXMLController ctrl) {
        loginctrl = ctrl;
    }

    public static void setStartCtrl(StartFXMLController ctrl) {
        startctrl = ctrl;
    }

    public static void setNewgameCtrl(NewgameFXMLController ctrl) {
        newgamectrl = ctrl;
    }

    public static void setLoadgameCtrl(LoadgameFXMLController ctrl) {
        loadgamectrl = ctrl;
    }

    public static void setInviteCtrl(InviteFXMLController ctrl) {
        Invitectrl = ctrl;
    }

    public static void setPlaymodeCtrl(PlayingModeFXMLController ctrl) {
        Playmodectrl = ctrl;
    }

    public static void setInvitationCtrl(InvitationFXMLController ctrl) {
        invitationCtrl = ctrl;
    }

    public static void setMultigameCtrl(MultigameFXMLController ctrl) {
        multigameCtrl = ctrl;
    }

    public static boolean getGameAccepted() {
        return gameAccepted;
    }

    public static void setReplay(boolean replay) {
        ClientHandler.replay = replay;
    }

    public static boolean getReplay() {
        return replay;
    }

    public static JSONArray getGames() {
        return gamesFullInfo;
    }

    public static ObservableList<String> getNameList() {
        return name;
    }

    public static ObservableList<String> getStatusList() {
        return status;
    }

    public static ObservableList<String> getScoreList() {
        return score;
    }

    public static char[][] getBoard() {
        return gameBoard;
    }

    public static void setIsLoaded(boolean isloaded) {
        isLoaded = isloaded;
    }

    public static boolean getIsLoaded() {
        return isLoaded;
    }

    public static void setisConnected(boolean isConnected) {
        ClientHandler.isConnected = isConnected;
    }

    public static boolean getisConnected() {
        return isConnected;
    }

    public static char getNextMove() {
        return nextMove;
    }

    public static String getNextPlayer() {
        return nextPlayer;
    }

    public static boolean getClientDropped() {
        return clientDropped;
    }

    // login handler with errors handler
    private static void Login(JSONObject response) {
        String request = response.get("type").toString();
        String resStatus = response.get("responseStatus").toString();
        if (resStatus.equals("true")) {
            player.setScore(Integer.parseInt(response.get("score").toString()));
            player.setUsername(response.get("username").toString());
            player.setStatus(response.get("status").toString());
            changeScene("Welcome");
        } else {
            String error = response.get("errorMsg").toString();
            String warning;

            if (request.equals("signin") && error.equals("signedin")) {
                warning = "You are already signed in, try to log in";
            } else if (request.equals("signin") && error.equals("fail")) {
                warning = "Wrong user name or password";
            } else if (request.equals("signup") && error.equals("fail")) {
                warning = "Username already exists, try anoter one";
            } else {
                warning = "unexpected Error try again!!";
            }
            Platform.runLater(() -> {
                loginctrl.getLabel().setText(warning);
            });
        }
    }

    // Updating the players list to the player
    private static void updateList(JSONObject response) {
        JSONObject JSONplayer = new JSONObject();
        JSONParser parser = new JSONParser();
        JSONArray list = (JSONArray) response.get("list");

        name = FXCollections.observableArrayList();
        status = FXCollections.observableArrayList();
        score = FXCollections.observableArrayList();

        for (int i = 0; i < list.size(); i++) {
            try {
                JSONplayer = (JSONObject) parser.parse(list.get(i).toString());
                name.add(JSONplayer.get("username").toString());
                score.add(JSONplayer.get("score").toString());
                status.add(JSONplayer.get("status").toString());
            } catch (ParseException ex) {
                System.out.println("Updated list can't be imported");
            }
        }
        switch (currentScene) {
            case "Start":
                Platform.runLater(() -> {
                    startctrl.updateTable(name, score, status);
                });
                break;
            case "Newgame":
                Platform.runLater(() -> {
                    newgamectrl.updateTable(name, score, status);
                });
                break;
            case "Loadgame":
                Platform.runLater(() -> {
                    loadgamectrl.updateTable(name, score, status);
                });
                break;
            case "Invite":
                Platform.runLater(() -> {
                    Invitectrl.updateTable(name, score, status);
                });
                break;
            case "PlayMode":
                Platform.runLater(() -> {
                    Playmodectrl.updateTable(name, score, status);
                });
                break;

            default:
                System.out.println("Player list not Error scene");
                break;
        }
    }

    // Creating invite object
    public static void invitePlayerRequest(String invitedPlayerUsername) {
        JSONObject inviteRequest = new JSONObject();
        inviteRequest.put("type", "invitePlayer");
        inviteRequest.put("username", invitedPlayerUsername);
        inviteRequest.put("newGame", true);
        ClientHandler.sendRequest(inviteRequest);
    }

    // Handling invition response according if the oppenont is not available
    private static void invitePlayerResponse(JSONObject response) {
        String resStatus = response.get("responseStatus").toString();

        if (resStatus.equals("false")) {
            if (currentScene.equals("Multigame")) {
                gameAccepted = false;
                Platform.runLater(() -> {
                    multigameCtrl.getWaitingLbl().setText(player.getOpponent() + " is not available");
                    multigameCtrl.getOkBtn().setDisable(false);
                });
            } else if (currentScene.equals("Loadgame")) {
                Platform.runLater(() -> {
                    loadgamectrl.getRejectionLabel().setText("Player is not currently available");
                    loadgamectrl.requestRejectionHandler();
                });
            }
        }
    }

    // Handling the invitation game scenarios
    private static void getInvitationResponse(JSONObject response) {
        String resStatus = response.get("responseStatus").toString();
        String inviteStatus = response.get("invitationStatus").toString();
        String username = response.get("username").toString();
        String newGame = response.get("newGame").toString();

        if (resStatus.equals("true")) {
            if (newGame.equals("true")) {
                newGameInviteHandler(username, inviteStatus);
            } else if (newGame.equals("false")) {
                replayGameInviteHandler(username, inviteStatus);
            }
        }
    }

    // New game invitation handler when accepted or not accepted
    private static void newGameInviteHandler(String username, String inviteStatus) {
        if (inviteStatus.equals("true")) {
            player.setInvited(false);
            player.setOpponent(username);
            gameAccepted = true;
            if (replay) {
                Platform.runLater(() -> {
                    multigameCtrl.getWaitingLbl().setText(username + " accepted your invitation, The game will start");
                });
            } else {
                Platform.runLater(() -> {
                    Invitectrl.getWaitingLbl().setText(username + " accepted your invitation, the game will start");
                });
            }
        } else {
            gameAccepted = false;
            if (replay) {
                Platform.runLater(() -> {
                    multigameCtrl.getWaitingLbl().setText(username + " didn't accepet your invitation");
                    multigameCtrl.getOkBtn().setDisable(false);
                });
            } else {
                Platform.runLater(() -> {
                    Invitectrl.getWaitingLbl().setText(username + " didn't accepet your invitation");
                    Invitectrl.getOkBtn().setDisable(false);
                });
            }
        }
    }

    // Replay game handler
    private static void replayGameInviteHandler(String username, String inviteStatus) {
        if (inviteStatus.equals("false")) {
            Platform.runLater(() -> {
                loadgamectrl.getRejectionLabel().setText("Player didn't accepet your request");
                loadgamectrl.requestRejectionHandler();
            });
        } else if (inviteStatus.equals("true")) {
            player.setOpponent(username);
        }
    }

    // Invitation request
    private static void invitationRequest(JSONObject request) {
        String username = request.get("username").toString();
        String newGame = request.get("newGame").toString();
        invitingUsername = username;
        ClientHandler.changeScene("Invitation");
        Platform.runLater(() -> {
            if (newGame.equals("false")) {
                String date = request.get("date").toString();
                invitationCtrl.getInvitationLabel().setText("Player " + username
                        + " is inviting you to play an old game.\n Date: [" + date + "]\n Do you accept?");
            } else {
                invitationCtrl.getInvitationLabel()
                        .setText("Player " + username + " is inviting you to a new game.\n Do you accept?");
            }
        });
    }

    // Setting opponent when invitation accepted
    public static void invitationResponse(String response) {
        JSONObject inviteResponse = new JSONObject();
        inviteResponse.put("type", "invitation");
        inviteResponse.put("invitationStatus", response);
        ClientHandler.sendRequest(inviteResponse);
        if ("true".equals(response)) {
            player.setOpponent(invitingUsername);
        }
    }

    // Game starting handler
    private static void GameStartedResponse(JSONObject response) {
        String resStatus = response.get("responseStatus").toString();
        if (resStatus.equals("true")) {

            if (replay) {
                if (player.getInvited()) {
                    Platform.runLater(() -> {
                        invitationCtrl.getWaitingLbl().setText("Start Playing");
                        invitationCtrl.getStartBtn().setDisable(false);
                    });
                    changeScene("Multigame");

                } else {
                    Platform.runLater(() -> {
                        multigameCtrl.getWaitingLbl().setText("Start Playing");
                        multigameCtrl.getOkBtn().setDisable(false);
                    });
                }
            } else {
                if (player.getInvited()) {
                    Platform.runLater(() -> {
                        invitationCtrl.getWaitingLbl().setText("Start Playing");
                        invitationCtrl.getStartBtn().setDisable(false);
                    });
                } else {
                    Platform.runLater(() -> {
                        Invitectrl.getWaitingLbl().setText("Start Playing");
                        Invitectrl.getOkBtn().setDisable(false);
                    });

                }
                changeScene("Multigame");
            }
        }
    }

    // Game endeing handler
    public static void gameEndedRequest(String winner, boolean isDraw, String errorMsg) {
        JSONObject gameEnded = new JSONObject();
        gameEnded.put("type", "gameEnded");
        gameEnded.put("responseStatus", "true");
        gameEnded.put("username", winner);
        gameEnded.put("isDraw", isDraw);
        gameEnded.put("errorMsg", errorMsg);
        ClientHandler.sendRequest(gameEnded);

        isLoaded = false;
    }

    // Game quit scenario response
    private static void gameQuitResponse(JSONObject response) {
        String resStatus = response.get("responseStatus").toString();
        if (resStatus.equals("true")) {
            gameAccepted = false;
            Platform.runLater(() -> {
                multigameCtrl.getWaitingSubscene().setVisible(true);
                multigameCtrl.getOkBtn().setDisable(false);

                multigameCtrl.getWaitingLbl().setText("Client connection dropped");
            });
        }
    }

    // Updating the score through the server to the database of this player
    private static void gameEndedResponse(JSONObject response) {
        String errormsg = response.get("errorMsg").toString();
        if (errormsg.equals("")) {
            String newScore = response.get("score").toString();
            player.setScore(Integer.parseInt(newScore));
        } else if (errormsg.equals("clientDropped")) {
            gameAccepted = false;
            Platform.runLater(() -> {
                multigameCtrl.getWaitingSubscene().setVisible(true);
                multigameCtrl.getWaitingLbl().setText("Client connection dropped");
            });
        }
        isLoaded = false;
    }

    // Submitting player moves
    public static void sendMoveRequest(int row, int col) {
        JSONObject sendMoveRequest = new JSONObject();
        sendMoveRequest.put("type", "sendMove");
        sendMoveRequest.put("row", (Integer) row);
        sendMoveRequest.put("col", (Integer) col);
        ClientHandler.sendRequest(sendMoveRequest);
    }

    // Getting the move from the server and set it in multi game
    private static void getMoveReponse(JSONObject response) {
        int row = Integer.parseInt(response.get("row").toString());
        int col = Integer.parseInt(response.get("col").toString());

        Game.CellPosition move = new Game.CellPosition(row, col);
        Game.setMoveOfNextPlayer(move);

        Platform.runLater(() -> {
            multigameCtrl.secondPlayerMove();
        });
    }

    // Saving game moves to the server
    public static void saveGameRequest(String nextMove) {
        JSONObject saveGame = new JSONObject();
        saveGame.put("type", "saveGame");
        saveGame.put("nextMove", nextMove);
        ClientHandler.sendRequest(saveGame);
        Platform.runLater(() -> {
            multigameCtrl.getSavingSubscene().setVisible(true);
        });
    }

    private static void saveGameResponse(JSONObject response) {
        String resStatus = response.get("responseStatus").toString();
        if (resStatus.equals("true")) {
            Platform.runLater(() -> {
                multigameCtrl.getSavingSubscene().setVisible(true);
                multigameCtrl.getSavingLbl().setText("Game saved successfully");
                multigameCtrl.getHomtBtn().setDisable(false);
            });
        }
    }

    // Load request handler
    public static void loadGameRequest(String invitedPlayerUsername, Long gameId) {
        JSONObject loadGameReq = new JSONObject();
        loadGameReq.put("type", "invitePlayer");
        loadGameReq.put("username", invitedPlayerUsername);
        loadGameReq.put("newGame", false);
        loadGameReq.put("gameId", gameId);
        sendRequest(loadGameReq);
    }

    // Loading the save game with X O postions
    private static void loadGameResponse(JSONObject response) {
        String resStatus = response.get("responseStatus").toString();
        JSONParser parser = new JSONParser();
        if (resStatus.equals("true")) {
            JSONArray board = (JSONArray) response.get("gameboard");
            nextMove = response.get("nextMove").toString().charAt(0);

            String xPlayer = response.get("xPlayer").toString();
            String oPlayer = response.get("oPlayer").toString();
            char[] board1D = new char[9];
            if (nextMove == 'X') {
                nextPlayer = xPlayer;
            } else if (nextMove == 'O') {
                nextPlayer = oPlayer;
            }
            for (int i = 0; i < board.size(); i++) {
                board1D[i] = board.get(i).toString().charAt(0);
            }
            gameBoard = Game.convertToTwoDimension(board1D);
            isLoaded = true;
            if (player.getInvited()) {
                Platform.runLater(() -> {
                    changeScene("Multigame");
                });
            } else {
                Platform.runLater(() -> {
                    changeScene("Multigame");
                });
            }
        }
    }

    // Load list of saved game
    private static void displayGamesList(JSONObject response) {
        games = FXCollections.observableArrayList();
        JSONParser parser = new JSONParser();
        gamesFullInfo = (JSONArray) response.get("gamesList");
        JSONObject game = new JSONObject();

        for (int i = 0; i < gamesFullInfo.size(); i++) {
            try {
                game = (JSONObject) parser.parse(gamesFullInfo.get(i).toString());
                games.add((i + 1) + ". [" + game.get("date").toString() + "] with " + game.get("player").toString());
            } catch (ParseException ex) {
                System.out.println("Can't fetch the game");
            }
        }
        Platform.runLater(() -> {
            loadgamectrl.displayGames(games);
        });
    }
}
