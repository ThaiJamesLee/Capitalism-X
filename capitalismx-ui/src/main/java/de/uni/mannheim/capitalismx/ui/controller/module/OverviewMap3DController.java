package de.uni.mannheim.capitalismx.ui.controller.module;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import de.uni.mannheim.capitalismx.ui.utils.Xform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.DepthTest;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.PerspectiveCamera;
import javafx.scene.SubScene;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class OverviewMap3DController extends GameModuleController {

	@FXML
	SubScene overviewMap3D;

	//Xforms allow 3D Rotation
	final Group root = new Group();
	final Xform axisGroup = new Xform();
	final Xform moleculeGroup = new Xform();
	final Xform terrainGroup = new Xform();
	final Xform world = new Xform();
	final PerspectiveCamera camera = new PerspectiveCamera(true);
	final Xform cameraXform = new Xform();
	final Xform cameraXform2 = new Xform();
	final Xform cameraXform3 = new Xform();

	private static final double CAMERA_INITIAL_DISTANCE = -700;
	private static final double CAMERA_INITIAL_X_ANGLE = 70.0;
	private static final double CAMERA_INITIAL_Y_ANGLE = 320.0;
	private static final double CAMERA_NEAR_CLIP = 0.1;
	private static final double CAMERA_FAR_CLIP = 3000.0;

	//TODO ausmisten - alle sinnvoll?
	private static final double CONTROL_MULTIPLIER = 0.1;
	private static final double SHIFT_MULTIPLIER = 10.0;
	private static final double MOUSE_SPEED = 0.1;
	private static final double ROTATION_SPEED = 2.0;
	private static final double TRACK_SPEED = 0.3;

	double mousePosX;
	double mousePosY;
	double mouseOldX;
	double mouseOldY;
	double mouseDeltaX;
	double mouseDeltaY;


	@Override
	public void update() {
		// TODO Auto-generated method stub

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		System.out.println("Map initialization started!");
		
		root.getChildren().add(world);
		root.setDepthTest(DepthTest.ENABLE);
		
		buildCamera();
		System.out.println("Success! - build camera");

		importModel();
		System.out.println("Success! - build model");

		overviewMap3D.setFill(Color.GREY);
		handleKeyboard(overviewMap3D, world);
		handleMouse(overviewMap3D, world);

		overviewMap3D.setCamera(camera);
		System.out.println("Map initialization finished!");
	}

	private void importModel() {
		FXMLLoader fxmlLoader = new FXMLLoader();
		fxmlLoader.setLocation(this.getClass().getClassLoader().getResource("models/HST-3DS/hst.fxml"));
		
		try {
			Group graphic = fxmlLoader.<Group>load();
			moleculeGroup.getChildren().addAll(graphic);
			root.getChildren().add(moleculeGroup);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}            

	}


	private void buildCamera() {

		root.getChildren().add(cameraXform);
		cameraXform.getChildren().add(cameraXform2);
		cameraXform2.getChildren().add(cameraXform3);
		cameraXform3.getChildren().add(camera);
		cameraXform3.setRotateZ(180.0);

		camera.setTranslateZ(-700);
		camera.setNearClip(0.1);
		camera.setFarClip(3000.0);
		camera.setFieldOfView(60);

		camera.setNearClip(CAMERA_NEAR_CLIP);
		camera.setFarClip(CAMERA_FAR_CLIP);
		camera.setTranslateZ(CAMERA_INITIAL_DISTANCE);
		cameraXform.ry.setAngle(CAMERA_INITIAL_Y_ANGLE);
		cameraXform.rx.setAngle(CAMERA_INITIAL_X_ANGLE);
	}


	//TODO welche Controls sind für die Map überhaupt sinnvoll -> absprechen!
	private void handleMouse(SubScene scene, final Node root) {
		scene.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override public void handle(MouseEvent me) {
				mousePosX = me.getSceneX();
				mousePosY = me.getSceneY();
				mouseOldX = me.getSceneX();
				mouseOldY = me.getSceneY();
			}
		});
		scene.setOnMouseDragged(new EventHandler<MouseEvent>() {
			@Override public void handle(MouseEvent me) {
				mouseOldX = mousePosX;
				mouseOldY = mousePosY;
				mousePosX = me.getSceneX();
				mousePosY = me.getSceneY();
				mouseDeltaX = (mousePosX - mouseOldX); 
				mouseDeltaY = (mousePosY - mouseOldY); 

				double modifier = 1.0;

				//TODO buttons um drehspeed zu ändern sinnvoll - klappt zumindest???
						if (me.isControlDown()) {
							modifier = CONTROL_MULTIPLIER;
						} 
						if (me.isShiftDown()) {
							modifier = SHIFT_MULTIPLIER;
						}     
						if (me.isPrimaryButtonDown()) {
							cameraXform.ry.setAngle(cameraXform.ry.getAngle() - mouseDeltaX*MOUSE_SPEED*modifier*ROTATION_SPEED);  
							//TODO auskommentieren für nur horizontale Kamerabewegung
							//		- oder doch besser nur updaten in bestimmtem Wertbereich
							cameraXform.rx.setAngle(cameraXform.rx.getAngle() + mouseDeltaY*MOUSE_SPEED*modifier*ROTATION_SPEED);  
						}
						else if (me.isSecondaryButtonDown()) {
							double z = camera.getTranslateZ();
							double newZ = z + mouseDeltaX*MOUSE_SPEED*modifier;
							camera.setTranslateZ(newZ);
						}
						else if (me.isMiddleButtonDown()) {
							cameraXform2.t.setX(cameraXform2.t.getX() + mouseDeltaX*MOUSE_SPEED*modifier*TRACK_SPEED);  
							cameraXform2.t.setY(cameraXform2.t.getY() + mouseDeltaY*MOUSE_SPEED*modifier*TRACK_SPEED);  
						}
			}
		});
	}

	@SuppressWarnings("incomplete-switch")//TODO
	private void handleKeyboard(SubScene scene, final Node root) {
		scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
			
			@Override
			public void handle(KeyEvent event) {
				switch (event.getCode()) {
				case Z:
					cameraXform2.t.setX(0.0);
					cameraXform2.t.setY(0.0);
					camera.setTranslateZ(CAMERA_INITIAL_DISTANCE);
					cameraXform.ry.setAngle(CAMERA_INITIAL_Y_ANGLE);
					cameraXform.rx.setAngle(CAMERA_INITIAL_X_ANGLE);
					break;
				case SHIFT:
					
				}
			}
		});
	}
}
