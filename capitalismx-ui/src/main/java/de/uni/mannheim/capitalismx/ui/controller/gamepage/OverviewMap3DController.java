package de.uni.mannheim.capitalismx.ui.controller.gamepage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import de.uni.mannheim.capitalismx.ui.eventhandler.Map3DInputHandler;
import de.uni.mannheim.capitalismx.ui.util.Xform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.DepthTest;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.PerspectiveCamera;
import javafx.scene.SceneAntialiasing;
import javafx.scene.SubScene;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.DrawMode;
import javafx.scene.shape.MeshView;
import javafx.scene.shape.TriangleMesh;
import javafx.stage.Screen;

/**
 * Controller for the 3D Map containg 3d assets of the  company and factory buildings.
 *  Based on the Oracle Tutorial for JavaFX 3D (-> https://docs.oracle.com/javase/8/javafx/graphics-tutorial/sampleapp3d.htm)
 * @author Alex
 *
 */

public class OverviewMap3DController implements Initializable {

	@FXML
	private AnchorPane overviewMap3D;

	final Group root = new Group();
	final Xform axisGroup = new Xform();
	final Xform moleculeGroup = new Xform();
	final Xform world = new Xform();
	final PerspectiveCamera camera = new PerspectiveCamera(true);
	final Xform cameraXform = new Xform();
	final Xform cameraXform2 = new Xform();
	final Xform cameraXform3 = new Xform();
	// TODO set good initial camera point for factory...
	private static final double CAMERA_INITIAL_DISTANCE = -4000;
	private static final double CAMERA_INITIAL_HEIGHT = -40;
	private static final double CAMERA_INITIAL_X_ANGLE = 0.0;
	private static final double CAMERA_INITIAL_Y_ANGLE = 0.0;
	private static final double CAMERA_NEAR_CLIP = 0.1;
	private static final double CAMERA_FAR_CLIP = 10000.0;
	
	private static final double AXIS_LENGTH = 1000.0;
	private static final float TERRAIN_LENGTH = 1000000;

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
	
	private Map3DInputHandler mouseEventHandler;

	public Map3DInputHandler getMouseEventHandler() {
		return mouseEventHandler;
	}

	private SubScene buildSubScene(Group group) {
		SubScene subscene = new SubScene(group, Screen.getPrimary().getBounds().getWidth(), Screen.getPrimary().getBounds().getHeight(), true, SceneAntialiasing.BALANCED);
		subscene.setFill(Color.SKYBLUE);
		// subscene.setPickOnBounds(true); //TODO ist das nötig?
		handleKeyboard(subscene, world);
		handleMouse(subscene, world);

		subscene.setCamera(camera);

		return subscene;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		root.getChildren().add(world);
		root.setDepthTest(DepthTest.ENABLE);

		buildCamera();
		//useful when for orientation and positioning when adding new Assets 
		//buildAxes();

		Group office = importModel("models/example_office/example_office.fxml");
		office.setTranslateX(1800);
		office.setTranslateZ(600);

		Group factory = importModel("models/Factory_Hall/industrial_building_1.fxml");

		moleculeGroup.getChildren().add(office);
		moleculeGroup.getChildren().add(factory);
		world.getChildren().addAll(moleculeGroup);

		MeshView terrain = generateTerrain(TERRAIN_LENGTH);
		world.getChildren().addAll(terrain);

		SubScene scene3D = buildSubScene(root);
		scene3D.setCamera(camera);

		overviewMap3D.getChildren().add(scene3D);
		this.mouseEventHandler = new Map3DInputHandler(cameraXform, cameraXform2, cameraXform3, camera);
	}

	/**
	 * loads 3d assets from xml file 
	 * @param location
	 * @return
	 */
	private Group importModel(String location) {
		FXMLLoader fxmlLoader = new FXMLLoader();
		fxmlLoader.setLocation(this.getClass().getClassLoader().getResource(location));
		Group graphic = new Group();
		try {
			graphic = fxmlLoader.<Group>load();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return graphic;
	}

	/**
	 * Initializes a moveable Camera instance from the
	 */
	private void buildCamera() {
		root.getChildren().add(cameraXform);
		cameraXform.getChildren().add(cameraXform2);
		cameraXform2.getChildren().add(cameraXform3);
		cameraXform3.getChildren().add(camera);
		// cameraXform3.setRotateZ(180.0);

		camera.setNearClip(CAMERA_NEAR_CLIP);
		camera.setFarClip(CAMERA_FAR_CLIP);
		camera.setTranslateZ(CAMERA_INITIAL_DISTANCE);
		camera.setTranslateY(CAMERA_INITIAL_HEIGHT);
		cameraXform.ry.setAngle(CAMERA_INITIAL_Y_ANGLE);
		cameraXform.rx.setAngle(CAMERA_INITIAL_X_ANGLE);
	}

	/**
	 * adds Axes of a 3 dimensional Coordinate System, 
	 * especially useful for orientation and positioning when adding new assets
	 */
	private void buildAxes() {
		final PhongMaterial redMaterial = new PhongMaterial();
		redMaterial.setDiffuseColor(Color.DARKRED);
		redMaterial.setSpecularColor(Color.RED);

		final PhongMaterial greenMaterial = new PhongMaterial();
		greenMaterial.setDiffuseColor(Color.DARKGREEN);
		greenMaterial.setSpecularColor(Color.GREEN);

		final PhongMaterial blueMaterial = new PhongMaterial();
		blueMaterial.setDiffuseColor(Color.DARKBLUE);
		blueMaterial.setSpecularColor(Color.BLUE);

		final Box xAxis = new Box(AXIS_LENGTH, 1, 1);
		final Box yAxis = new Box(1, AXIS_LENGTH, 1);
		final Box zAxis = new Box(1, 1, AXIS_LENGTH);

		xAxis.setMaterial(redMaterial);
		yAxis.setMaterial(greenMaterial);
		zAxis.setMaterial(blueMaterial);

		axisGroup.getChildren().addAll(xAxis, yAxis, zAxis);
		axisGroup.setVisible(true);
		world.getChildren().addAll(axisGroup);
	}

	/**
	 * generates a green ground {@link MeshView}, size defined by parameter
	 * @param side
	 * @return
	 */
	private MeshView generateTerrain(float side) {
		TriangleMesh mesh = new TriangleMesh();
		mesh.getTexCoords().addAll(0, 0);

		float h = 0.1f;
		mesh.getPoints().addAll(0, 0, 0, 0, h, -side / 2, // Point 1 - Front
				-side / 2, h, 0, // Point 2 - Left
				side / 2, h, 0, // Point 3 - Back
				0, h, side / 2 // Point 4 - Right
		);

		mesh.getFaces().addAll(0, 0, 2, 0, 1, 0, // Front left face
				0, 0, 1, 0, 3, 0, // Front right face
				0, 0, 3, 0, 4, 0, // Back right face
				0, 0, 4, 0, 2, 0, // Back left face
				3, 0, 0, 0, 1, 0, // Bottom rear face
				3, 0, 2, 0, 0, 0 // Bottom front face
		);

		MeshView terrainMesh = new MeshView(mesh);
		terrainMesh.setDrawMode(DrawMode.FILL);

		final PhongMaterial greenMaterial = new PhongMaterial();
		greenMaterial.setDiffuseColor(Color.LIGHTGREEN);

		terrainMesh.setMaterial(greenMaterial);

		terrainMesh.setTranslateZ(-1.0);
		return terrainMesh;
	}

	private void handleMouse(SubScene scene, final Node root) {
		scene.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent me) {
				mousePosX = me.getSceneX();
				mousePosY = me.getSceneY();
				mouseOldX = me.getSceneX();
				mouseOldY = me.getSceneY();
			}
		});
		scene.setOnMouseDragged(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent me) {
				mouseOldX = mousePosX;
				mouseOldY = mousePosY;
				mousePosX = me.getSceneX();
				mousePosY = me.getSceneY();
				mouseDeltaX = (mousePosX - mouseOldX);
				mouseDeltaY = (mousePosY - mouseOldY);

				double modifier = 1.0;

				if (me.isControlDown()) {
					modifier = CONTROL_MULTIPLIER;
				}
				if (me.isShiftDown()) {
					modifier = SHIFT_MULTIPLIER;
				}
				if (me.isPrimaryButtonDown()) {
					cameraXform.ry.setAngle(
							cameraXform.ry.getAngle() + mouseDeltaX * MOUSE_SPEED * modifier * ROTATION_SPEED);
					// System.out.println("ry: " + (cameraXform.ry.getAngle() +
					// mouseDeltaX*MOUSE_SPEED*modifier*ROTATION_SPEED));
					// to ensure that camera stays overground
					double newX = cameraXform.rx.getAngle() - mouseDeltaY * MOUSE_SPEED * modifier * ROTATION_SPEED;
					if (-180 < newX && newX <= 0) {
						cameraXform.rx.setAngle(newX);
					}
					// System.out.println("rX: " + newX);
				} else if (me.isSecondaryButtonDown()) {
					cameraXform2.t.setX(cameraXform2.t.getX() + mouseDeltaX * MOUSE_SPEED * modifier * TRACK_SPEED);
					cameraXform2.t.setY(cameraXform2.t.getY() + mouseDeltaY * MOUSE_SPEED * modifier * TRACK_SPEED);
				}
			}
		});
		scene.addEventHandler(ScrollEvent.SCROLL, event -> {
			double delta = event.getDeltaY();
			camera.translateZProperty().set(camera.getTranslateZ() + delta);
		});
		scene.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
			switch (event.getCode()) {
			case W:
				cameraXform2.t.setZ(cameraXform2.t.getZ() + 10);
				break;
			case A:
				cameraXform2.t.setX(cameraXform2.t.getX() + 10);
				break;
			case S:
				cameraXform2.t.setZ(cameraXform2.t.getZ() - 10);
				break;
			case D:
				cameraXform2.t.setX(cameraXform2.t.getX() - 10);
				break;
			default:
				break;

			}
		});
	}

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
				case X:
					axisGroup.setVisible(!axisGroup.isVisible());
					break;
				case W:
					cameraXform2.t.setZ(cameraXform2.t.getZ() + 10);
					break;
				case A:
					cameraXform2.t.setX(cameraXform2.t.getX() + 10);

					break;
				case S:
					cameraXform2.t.setZ(cameraXform2.t.getZ() - 10);
					break;
				case D:
					cameraXform2.t.setX(cameraXform2.t.getX() - 10);
					break;
				}
			}
		});
	}

}
