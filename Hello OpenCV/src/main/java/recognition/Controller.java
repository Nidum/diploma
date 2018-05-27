package recognition;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.bytedeco.javacv.CanvasFrame;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameGrabber;
import org.bytedeco.javacv.OpenCVFrameGrabber;

/**
 * Created by Emiliia Nesterovych on 1/6/2018.
 */
public class Controller {
    @FXML
    private Canvas canvas;
    @FXML
    private Button startBtn;

    public void init(Stage primaryStage) {
        CanvasFrame canvas = new CanvasFrame("Webcam");
        canvas.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
        FrameGrabber grabber = new OpenCVFrameGrabber("");

        try {
            grabber.start();
            Frame img;
            //GraphicsContext graphicsContext2D = canvas.getGraphicsContext2D();
            while (true) {
                img = grabber.grab();
               canvas.setCanvasSize(grabber.getImageWidth(), grabber.getImageHeight());
                if (img != null) {

                    canvas.showImage(img);
                    //graphicsContext2D.
                }
            }
        }
        catch (Exception e) {

        }
    }
}
