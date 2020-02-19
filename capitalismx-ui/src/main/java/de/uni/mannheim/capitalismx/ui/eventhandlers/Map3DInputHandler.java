package de.uni.mannheim.capitalismx.ui.eventhandlers;

import de.uni.mannheim.capitalismx.ui.application.UIManager;
import de.uni.mannheim.capitalismx.ui.utils.Xform;
import javafx.event.EventHandler;
import javafx.scene.Camera;
import javafx.scene.input.InputEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;

/**
 * Implementation of an EventHandler for {@link InputEvent}s, that regard the
 * 3D-Map in the Game.
 */
public class Map3DInputHandler implements EventHandler<InputEvent> {

	private double mousePosY, mousePosX, mouseDeltaX, mouseDeltaY;

	private static final double CONTROL_MULTIPLIER = 0.1;
	private static final double SHIFT_MULTIPLIER = 10.0;
	private static final double MOUSE_SPEED = 0.1;
	private static final double ROTATION_SPEED = 2.0;

	Xform cameraXform = null;
	Xform cameraXform2 = null;
	Xform cameraXform3 = null;
	Camera camera = null;

	public Map3DInputHandler(Xform camXform1, Xform camXform2, Xform camXform3, Camera camera) {
		this.cameraXform = camXform1;
		this.cameraXform2 = camXform2;
		this.cameraXform3 = camXform3;
		this.camera = camera;
	}

	/**
	 * Method handles a mouse-drag-event and converts it into camera rotation.
	 * 
	 * @param me The {@link MouseEvent} containing the drag.
	 */
	public void handleDrag(MouseEvent me) {
		double mouseOldX = mousePosX;
		double mouseOldY = mousePosY;
		mousePosX = me.getSceneX();
		mousePosY = me.getSceneY();
		mouseDeltaX = (mousePosX - mouseOldX);
		mouseDeltaY = (mousePosY - mouseOldY);

		// Fixes an issue with detected drag when opening a PopOver while in the
		// Map-GameView
		if (Math.abs(mouseDeltaX) > 200 || Math.abs(mouseDeltaY) > 200)
			return;

		double modifier = 1.0;

		if (me.isControlDown()) {
			modifier = CONTROL_MULTIPLIER;
		}
		if (me.isShiftDown()) {
			modifier = SHIFT_MULTIPLIER;
		}
		if (me.isPrimaryButtonDown()) {
			cameraXform.ry.setAngle(cameraXform.ry.getAngle() + mouseDeltaX * MOUSE_SPEED * modifier * ROTATION_SPEED);
			// to ensure that camera stays overground
			double newX = cameraXform.rx.getAngle() - mouseDeltaY * MOUSE_SPEED * modifier * ROTATION_SPEED;
			if (-180 < newX && newX <= 0) {
				cameraXform.rx.setAngle(newX);
			}
			// System.out.println("rX: " + newX);
		} else if (me.isSecondaryButtonDown()) {
			cameraXform2.t.setX(cameraXform2.t.getX() - mouseDeltaX * modifier);
			cameraXform2.t.setY(cameraXform2.t.getY() - mouseDeltaY * modifier);
		}
	}

	@Override
	public void handle(InputEvent event) {
		// only handle events if the controls for the 3D-map are enabled
		if (UIManager.getInstance().getGamePageController().isMapControlsEnabled()) {
			if (event instanceof MouseEvent) {
				/**
				 * Handle MouseEvents
				 */
				MouseEvent me = (MouseEvent) event;
				if (me.getEventType().equals(MouseEvent.MOUSE_PRESSED)) {
					// save current mouse position
					mousePosX = me.getSceneX();
					mousePosY = me.getSceneY();
				} else if (me.getEventType().equals(MouseEvent.MOUSE_DRAGGED)) {
					handleDrag(me);
				}
			} else if (event instanceof ScrollEvent) {
				/**
				 * Handle ScrollEvents
				 */
				ScrollEvent se = (ScrollEvent) event;
				double delta = se.getDeltaY();
				camera.translateZProperty().set(camera.getTranslateZ() + delta);
			}
		}
	}

}
