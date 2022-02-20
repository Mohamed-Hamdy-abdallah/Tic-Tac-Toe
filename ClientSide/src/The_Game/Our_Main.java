///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package The_Game;
//
//import java.io.IOException;
//import java.util.Random;
//import javafx.application.Application;
//import javafx.event.ActionEvent;
//import javafx.event.EventHandler;
//import javafx.geometry.Insets;
//import javafx.geometry.Pos;
//import javafx.scene.Scene;
//import javafx.scene.control.Button;
//import javafx.scene.control.Label;
//import javafx.scene.layout.GridPane;
//import javafx.scene.layout.HBox;
//import javafx.scene.layout.StackPane;
//import javafx.scene.layout.VBox;
//import javafx.scene.text.Font;
//import javafx.stage.Stage;
//import login.Login;
//
///**
// *
// * @author Mohamed Hamdy
// */
//public class Our_Main extends Application {
//
////    Random rand = new Random();
//
//
//    @Override
//    public void start(Stage primaryStage) throws IOException {
//        Login log = new Login () ;
//        primaryStage.setScene(log.get_scene());
//        primaryStage.show();
//
//        //GUI_diffculity diff = new GUI_diffculity();
//        //primaryStage.setScene(diff.get_scene());
//        //primaryStage.show();
////        Grid_GUI g = new Grid_GUI();
////        diff.easy.setOnAction(e -> {
////        game_functions.difficulty=1 ;
////        primaryStage.setScene(g.get_scene());
////        primaryStage.show();
////        });
////        diff.hard.setOnAction(e -> {
////        game_functions.difficulty=2 ;
////        primaryStage.setScene(g.get_scene());
////        primaryStage.show();
////        });
//
////        VBox root = new VBox();
////        root.setId("root");
////        HBox top = new HBox();
////        top.setId("top");
////        HBox winner_result=new HBox();
////        Button newGame = new Button("New Game");
////        //newGame.setFont(new Font(20));
////        newGame.setMinWidth(200);
////        newGame.setId("newGame");
////
////        Button quit = new Button("Quit");
////        //quit.setFont(new Font(20));
////        quit.setMinWidth(200);
////        quit.setId("quit");
////        GridPane grid = new GridPane();
////        grid.setId("grid");
////        Button btn[][] = new Button[3][3];
////        Label winner = new Label();
////        Label result = new Label();
////
////          int i, j;
////        for(i=0; i<3; i++)
////            for(j=0; j<3; j++)
////            {
////                int x = i, y = j;
////                btn[i][j] = new Button("");
////                grid.add(btn[i][j], j, i);
////                btn[i][j].setPrefWidth(200);
////                btn[i][j].setPrefHeight(200);
////                btn[i][j].setFont(new Font(70));
////                btn[i][j].setOnAction(e ->
////                {
////                    int xCount = 0, oCount = 0;
////                    String win = "";
////                    //result mean no one played or during game
////                    if(result.getText().equals(""))
////                    {
////
////
////	                        xCount = 0;
////	                        for (int a = 0; a < 3; a++)
////	                            for (int b = 0; b < 3; b++)
////	                                if (btn[a][b].getText().equals("X"))
////	                                    xCount++;
////	                        if (btn[x][y].getText().equals(""))
////	                        {
////	                        	btn[x][y].setText("X");
////	                            btn[x][y].setId("X");
////	                            xCount++;
////		                        win = game_functions.check(btn);
////			                    if (!win.equals(""))
////			                    {
////			                        winner.setId("winner");
////			                        winner.setText("Winner");
////			                        result.setId(win);
////			                        result.setText(win);
////
////			                    }
////			                    else if(xCount == 5)
////			                    {
////			                        winner.setId("winner");
////			                        winner.setText("DRAW");
////			                    }
////			                    if(winner.getText().equals(""))
////			                    	game_functions.fillO(btn);
////			                    win = game_functions.check(btn);
////			                    if (!win.equals(""))
////			                    {
////			                        winner.setId("winner");
////			                        winner.setText("Winner");
////			                        result.setId(win);
////			                        result.setText(win);
////
////			                    }
////	                        }
////
////
////                    }
////                });
////            }
////         newGame.setOnAction(e ->
////        {
////            for(int a = 0; a < 3; a++)
////                for(int b = 0; b < 3; b++)
////                {
////                    btn[a][b].setText("");
////                    btn[a][b].setId("empty");
////                }
////
////            winner.setId("");
////            winner.setText("");
////            result.setId("");
////            result.setText("");
////        });
////
////       quit.setOnAction(e -> System.exit(0));
////
////        newGame.setPrefWidth(100);
////        HBox.setMargin(newGame, new Insets(10, 0, 10, 15));
////        quit.setPrefWidth(100);
////        HBox.setMargin(quit, new Insets(10, 0, 10, 20));
////        VBox.setMargin(grid, new Insets(20, 20, 20, 20));
////        top.getChildren().addAll(newGame, quit);
////        top.setAlignment(Pos.CENTER);
////        winner.setAlignment(Pos.CENTER);
////        winner.setPrefWidth(160);
////        winner.setPrefHeight(70);
////        winner.setFont(new Font(40));
////        VBox.setMargin(winner, new Insets(0, 0, 0, 240));
////        result.setPrefWidth(70);
////        result.setPrefHeight(70);
////        result.setAlignment(Pos.CENTER);
////        VBox.setMargin(result, new Insets(0, 0, 0, 280));
////        ///////////////////////////////
////        winner_result.getChildren().addAll(winner,result);
////        winner_result.setAlignment(Pos.CENTER);
////        //HBox.setMargin(winner_result,new Insets(0, 0, 0, 240));
////        root.getChildren().addAll(top, grid, winner_result);
////        primaryStage.setTitle("Tic Tac Toe");
////        Scene scene = new Scene(root, 640, 820);
////        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
////        primaryStage.setScene(scene);
////        primaryStage.show();
//    }
//
////    String check(Button[][] a)
////    {
////        int i;
////        for(i=0;i<3;i++)
////        {
////            if(a[i][0].getText().equals(a[i][1].getText()) && a[i][1].getText().equals(a[i][2].getText()) && !a[i][2].getText().equals(""))
////                return(a[i][0].getText());
////            else if(a[0][i].getText().equals(a[1][i].getText()) && a[1][i].getText().equals(a[2][i].getText()) && !a[2][i].getText().equals(""))
////                return(a[0][i].getText());
////        }
////        if((a[0][0].getText().equals(a[1][1].getText()) && a[1][1].getText().equals(a[2][2].getText())) || (a[0][2].getText().equals(a[1][1].getText()) && a[1][1].getText().equals(a[2][0].getText())) && !a[1][1].getText().equals(""))
////            return(a[1][1].getText());
////        return("");
////    }
////
////    void fillO(Button btn[][])
////    {
////    	int a, b, xc = 0, oc = 0, oCount = 0;
////    	for(a = 0; a < 3; a++)
////    		for(b = 0; b < 3; b++)
////    			if(btn[a][b].getText().equals("O"))
////    				oCount++;
////   	if(oCount < 4 ) //&& lastWin == 'X') || (oCount < 5 && lastWin == 'O')
////    	{
////    		for(a = 0; a < 3; a++)
////	    	{
////	    		xc = oc = 0;
////	    		for(b = 0; b < 3; b++)
////	    			if(btn[a][b].getText().equals("O"))
////	    				oc++;
////	    			else if(btn[a][b].getText().equals("X"))
////	    				xc++;
////	    		if(oc==2)
////	    			break;
////	    	}
////	    	if(oc==2 && xc==0)
////	    	{
////	    		for(b = 0; b <3; b++)
////	    			if(btn[a][b].getText().equals(""))
////	    			{
////	    				btn[a][b].setText("O");
////	    				btn[a][b].setId("O");
////	    			}
////	    	}
////	    	else
////	    	{
////		    	for(a = 0; a < 3; a++)
////		    	{
////		    		xc = oc = 0;
////		    		for(b = 0; b < 3; b++)
////		    			if(btn[b][a].getText().equals("O"))
////		    				oc++;
////		    			else if(btn[b][a].getText().equals("X"))
////		    				xc++;
////		    		if(oc==2)
////		    			break;
////		    	}
////		    	if(oc==2 && xc==0)
////		    	{
////		    		for(b = 0; b <3; b++)
////		    			if(btn[b][a].getText().equals(""))
////		    			{
////		    				btn[b][a].setText("O");
////		    				btn[b][a].setId("O");
////		    			}
////		    	}
////		    	else
////		    	{
////		    		xc = oc = 0;
////		    		for(a = 0; a < 3; a++)
////		    			if(btn[a][a].getText().equals("O"))
////		    				oc++;
////		    			else if(btn[a][a].getText().equals("X"))
////		    				xc++;
////		    		if(oc==2 && xc==0)
////		    		{
////		    			for(a = 0; a < 3; a++)
////		    				if(btn[a][a].getText().equals(""))
////		    				{
////		    					btn[a][a].setText("O");
////		    					btn[a][a].setId("O");
////		    				}
////		    		}
////		    		else
////		    		{
////		    			xc = oc = 0;
////			    		for(a = 0; a < 3; a++)
////			    			if(btn[a][2-a].getText().equals("O"))
////			    				oc++;
////			    			else if(btn[a][2-a].getText().equals("X"))
////			    				xc++;
////			    		if(oc==2 && xc==0)
////			    		{
////			    			for(a = 0; a < 3; a++)
////			    				if(btn[a][2-a].getText().equals(""))
////			    				{
////			    					btn[a][2-a].setText("O");
////			    					btn[a][2-a].setId("O");
////			    				}
////			    		}
////			    		else
////			        	{
////			    			for(a = 0; a < 3; a++)
////			    	    	{
////			    	    		xc = oc = 0;
////			    	    		for(b = 0; b < 3; b++)
////			    	    			if(btn[a][b].getText().equals("O"))
////			    	    				oc++;
////			    	    			else if(btn[a][b].getText().equals("X"))
////			    	    				xc++;
////			    	    		if(xc==2)
////			    	    			break;
////			    	    	}
////			    	    	if(xc==2 && oc==0)
////			    	    	{
////			    	    		for(b = 0; b <3; b++)
////			    	    			if(btn[a][b].getText().equals(""))
////			    	    			{
////			    	    				btn[a][b].setText("O");
////			    	    				btn[a][b].setId("O");
////			    	    			}
////			    	    	}
////			    	    	else
////			    	    	{
////			    		    	for(a = 0; a < 3; a++)
////			    		    	{
////			    		    		xc = oc = 0;
////			    		    		for(b = 0; b < 3; b++)
////			    		    			if(btn[b][a].getText().equals("O"))
////			    		    				oc++;
////			    		    			else if(btn[b][a].getText().equals("X"))
////			    		    				xc++;
////			    		    		if(xc==2)
////			    		    			break;
////			    		    	}
////			    		    	if(xc==2 && oc==0)
////			    		    	{
////			    		    		for(b = 0; b <3; b++)
////			    		    			if(btn[b][a].getText().equals(""))
////			    		    			{
////			    		    				btn[b][a].setText("O");
////			    		    				btn[b][a].setId("O");
////			    		    			}
////			    		    	}
////			    		    	else
////			    		    	{
////			    		    		xc = oc = 0;
////			    		    		for(a = 0; a < 3; a++)
////			    		    			if(btn[a][a].getText().equals("O"))
////			    		    				oc++;
////			    		    			else if(btn[a][a].getText().equals("X"))
////			    		    				xc++;
////			    		    		if(xc==2 && oc==0)
////			    		    		{
////			    		    			for(a = 0; a < 3; a++)
////			    		    				if(btn[a][a].getText().equals(""))
////			    		    				{
////			    		    					btn[a][a].setText("O");
////			    		    					btn[a][a].setId("O");
////			    		    				}
////			    		    		}
////			    		    		else
////			    		    		{
////			    		    			xc = oc = 0;
////			    			    		for(a = 0; a < 3; a++)
////			    			    			if(btn[a][2-a].getText().equals("O"))
////			    			    				oc++;
////			    			    			else if(btn[a][2-a].getText().equals("X"))
////			    			    				xc++;
////			    			    		if(xc==2 && oc==0)
////			    			    		{
////			    			    			for(a = 0; a < 3; a++)
////			    			    				if(btn[a][2-a].getText().equals(""))
////			    			    				{
////			    			    					btn[a][2-a].setText("O");
////			    			    					btn[a][2-a].setId("O");
////			    			    				}
////			    			    		}
////			    			    		else
////			    			        	{
////			    			        		do
////			    			        		{
////			    			        			a = rand.nextInt(3);
////			    			        			b = rand.nextInt(3);
////			    			        		}while(btn[a][b].getText().equals("X") || btn[a][b].getText().equals("O"));
////			    			        		btn[a][b].setText("O");
////			    			        		btn[a][b].setId("O");
////			    			        	}
////			    		    		}
////			    		    	}
////			    	    	}
////			        	}
////		    		}
////		    	}
////	    	}
////    	}
////    }
//
//    /**
//     * @param args the command line arguments
//     */
//    public static void main(String[] args) {
//        launch(args);
//    }
//
//}
