package sample;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import sample.sprites.Sprite;

/**
 * Created by steve on 3/6/17.
 */
public class MouseGestures {

    final DragContext dragContext = new DragContext();

    public void makeDraggable(final Sprite sprite) {

        sprite.setOnMousePressed(onMousePressedEventHandler);
        sprite.setOnMouseDragged(onMouseDraggedEventHandler);
        sprite.setOnMouseReleased(onMouseReleasedEventHandler);

    }

    EventHandler<MouseEvent> onMousePressedEventHandler = new EventHandler<MouseEvent>() {

        @Override
        public void handle(MouseEvent event) {

            dragContext.x = event.getSceneX();
            dragContext.y = event.getSceneY();

        }
    };

    EventHandler<MouseEvent> onMouseDraggedEventHandler = new EventHandler<MouseEvent>() {

        @Override
        public void handle(MouseEvent event) {

            Sprite sprite = (Sprite) event.getSource();

            double offsetX = event.getSceneX() - dragContext.x;
            double offsetY = event.getSceneY() - dragContext.y;

            sprite.setLocationOffset(offsetX, offsetY);

            dragContext.x = event.getSceneX();
            dragContext.y = event.getSceneY();

        }
    };

    EventHandler<MouseEvent> onMouseReleasedEventHandler = new EventHandler<MouseEvent>() {

        @Override
        public void handle(MouseEvent event) {

        }
    };

    class DragContext {

        double x;
        double y;

    }

}