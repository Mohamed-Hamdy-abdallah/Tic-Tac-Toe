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
            clientSocket = new Socket("127.0.0.1", 9090);
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
                case "sendChat":
                    sendChatResponse(jsonMsg);
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
                ex.printStackTrace();
            }
        });
    }

    // Setters and getters for the scene here
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

    // Setter and getter for the player here
    public static void setPlayer(Player p) {
        player = p;
    }

    public static Player getPlayer() {
        return player;
    }

    // setters and getters for multi scences according to the player
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

    // login handler
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
                warning = "unexpected";
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
                break;
        }
    }

    // Invitation handler
    public static void invitePlayerRequest(String invitedPlayerUsername) {
        JSONObject inviteRequest = new JSONObject();
        inviteRequest.put("type", "invitePlayer");
        inviteRequest.put("username", invitedPlayerUsername);
        inviteRequest.put("newGame", true);
        ClientHandler.sendRequest(inviteRequest);
    }

    private static void invitePlayerResponse(JSONObject response) {
        String resStatus = response.get("responseStatus").toString();

        if (resStatus.equals("false")) {
            if (currentScene.equals("Multigame")) {
                gameAccepted = false;
                Platform.runLater(() -> {
                    multigameCtrl.getWaitingLbl().setText(player.getOpponent() + " is not available.");
                    multigameCtrl.getOkBtn().setDisable(false);
                });
            } else if (currentScene.equals("Loadgame")) {
                Platform.runLater(() -> {
                    loadgamectrl.getRejectionLabel().setText("Player is not available.");
                    loadgamectrl.requestRejectionHandler();
                });
            }
        }
    }

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
}
