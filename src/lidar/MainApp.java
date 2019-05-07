package lidar;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import lidar.model.AlsParameterModel;
import lidar.model.BeamParameterModel;
import lidar.model.DeviceParameterModel;
import lidar.model.MlsParameterModel;
import lidar.model.MonoPulseParameterModel;
import lidar.model.TlsParameterModel;
import lidar.view.AlsParameterController;
import lidar.view.BeamParameterController;
import lidar.view.DeviceParameterController;
import lidar.view.MlsParameterController;
import lidar.view.MonoPulseParameterController;
import lidar.view.RootLayoutController;
import lidar.view.TextParameterViewController;
import lidar.view.TlsParameterController;

public class MainApp extends Application {

	private Stage primaryStage;
	
	private BorderPane rootLayout;
	public RootLayoutController rootLayoutController;
	
	public DeviceParameterModel deviceParameterModel;
	public BeamParameterModel beamParameterModel;
	public MonoPulseParameterModel monoPulseParameterModel;
	public AlsParameterModel alsParameterModel;
	public TlsParameterModel tlsParameterModel;
	public MlsParameterModel mlsParameterModel;
	
	@Override
	public void start(Stage primaryStage) {
		instantiate();
		
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("LiDAR Simulation");
		
		initRootLayout();
		
		Scene scene = new Scene(rootLayout);
		primaryStage.setScene(scene);
		primaryStage.show();
		
		update();	
	}
	
	public void update() {
		
		rootLayoutController.clear();
		
		addDeviceParameter();
		addBeamParameter();
		
		addTextParameterPlatform(new MonoPulseParameterController(monoPulseParameterModel), "Mono Pulse");
		addTextParameterPlatform(new AlsParameterController(alsParameterModel), "ALS");
		addTextParameterPlatform(new TlsParameterController(tlsParameterModel), "TLS");
		addTextParameterPlatform(new MlsParameterController(mlsParameterModel), "MLS");
	}
	
	private void addDeviceParameter() {
		DeviceParameterController deviceParameterController = new DeviceParameterController(deviceParameterModel);
		rootLayoutController.generalParameterVBox.getChildren().add(deviceParameterController.parameterAnchorPane);
	}
	
	private void addBeamParameter() {
		BeamParameterController controller = new BeamParameterController(beamParameterModel);
		rootLayoutController.generalParameterVBox.getChildren().add(controller.parameterAnchorPane);		
	}
	
	private void addTextParameterPlatform(TextParameterViewController controller, String tabText) {
		Tab tab = new Tab();
		tab.setText(tabText);
		tab.setClosable(false);
		tab.setContent(controller.parameterAnchorPane);
		rootLayoutController.platformTabPane.getTabs().add(tab);
	}
	
	private void instantiate() {
		deviceParameterModel = new DeviceParameterModel();		
		beamParameterModel = new BeamParameterModel();
		monoPulseParameterModel = new MonoPulseParameterModel();
		alsParameterModel = new AlsParameterModel();
		tlsParameterModel = new TlsParameterModel();
		mlsParameterModel = new MlsParameterModel();
	}
	
	private void initRootLayout() {
		
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/RootLayout.fxml"));
			rootLayout = (BorderPane) loader.load();
			
			rootLayoutController = loader.getController();
			rootLayoutController.setMainApp(this);
	
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public Stage getPrimaryStage() {
		return primaryStage;
	}

	public static void main(String[] args) {
		launch(args);
	}
}
