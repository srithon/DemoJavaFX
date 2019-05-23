package thoniyil.sridaran.javafxdemo.main;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.*;
import javafx.scene.media.AudioClip;
import java.net.URL;
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.event.*;
import javafx.scene.input.*;
import javafx.scene.text.*;
import java.io.File;

public class Main extends Application implements EventHandler<InputEvent>
{
	private GraphicsContext gc;
	private int imageX;
	private AnimateObjects anim;
	private Image trooper;

	private int imageXInit;
	private int imageYInit;

	private AudioClip hitSound;

	private Stage st;

	{
		imageX = 0;
		imageXInit = 180;
		imageYInit = 100;
	}

	public Main()
	{

	}

	public static void main(String[] args)
	{
		launch(args);
	}

	public void start(Stage st)
	{
		this.st = st;
		st.setTitle("Final Project Title");
		init(st);
		st.show();
	}

	public void handle(final InputEvent event)
	{
		if (((KeyEvent) event).getCode() == KeyCode.LEFT)
		{
			imageX--;
		}
		else if (((KeyEvent) event).getCode() == KeyCode.RIGHT)
		{
			imageX++;
		}
		else if (((KeyEvent) event).getCode() == KeyCode.SPACE)
		{
			System.out.println("DFGDF");
			init(st);
		}
	}

	private void init(Stage s)
	{
		imageX = 0;

		Group root = new Group();
		Canvas c = new Canvas(800, 400);
		root.getChildren().add(c);
		Scene sc = new Scene(root);

		gc = c.getGraphicsContext2D();
		trooper = new Image("file:trooper.jpg");
		gc.drawImage(trooper, imageXInit, imageYInit);

		hitSound = new AudioClip(getClass().getResource("test.wav").toString());
		hitSound.play();

		anim = new AnimateObjects();
		anim.start();
		sc.addEventHandler(KeyEvent.KEY_PRESSED, this);

		st.setScene(sc);
	}

	private class AnimateObjects extends AnimationTimer
	{
		private int dir;

		private Rectangle2D rect1;

		public AnimateObjects()
		{
			super();
			dir = 1;

			rect1 = new Rectangle2D(400, 100, 100, 100);
		}

		public void handle(long now)
		{
			if (imageX > -50)
			{
				if (imageX == 400)
					dir = -1;
				else if (imageX == 0)
					dir = 1;

				//imageX += dir;
				gc.drawImage(trooper, imageXInit + imageX, imageYInit);

				gc.fillRect(400, 100, 100, 100);

				Rectangle2D rect2 = new Rectangle2D(imageXInit + imageX, imageYInit, trooper.getWidth(), trooper.getHeight());

				if (rect1.intersects(rect2))
				{
					System.out.println("HIT");
					//hitSound.play();
				}
			}
			else
			{
				init(st);

				// we are going to display Game over if the user moves 50 pixels to the left
				gc.setFill( Color.YELLOW); //Fills the text in yellow
				gc.setStroke( Color.BLACK ); //Changes the outline the black
				gc.setLineWidth(1); //How big the black lines will be
				Font font = Font.font("Arial", FontWeight.NORMAL, 48 );
				gc.setFont( font );
				gc.fillText("Game Over", 100, 50 ); //draws the yellow part of the text
				gc.strokeText("Game Over", 100, 50 ); //draws the outline part of the text
				gc.setFill(Color.BLACK);
				gc.setStroke(Color.BLACK);
			}
		}
	}
}
