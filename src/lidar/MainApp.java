package lidar;

import java.nio.file.Files;
import java.nio.file.Paths;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import lidar.model.AlsParameterModel;
import lidar.model.BeamParameterModel;
import lidar.model.DeviceParameterModel;
import lidar.model.MlsParameterModel;
import lidar.model.Model;
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
import utils.Const;

public class MainApp extends Application {

	private Stage primaryStage;
	
	private BorderPane rootLayout;
	private RootLayoutController rootLayoutController;
	
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
	
//	private void addAlsParameter() {
//		AlsParameterController controller = new AlsParameterController(alsParameterModel);
//		Tab tab = new Tab();
//		tab.setText("ALS");
//		tab.setClosable(false);
//		tab.setContent(controller.parameterAnchorPane);
//		rootLayoutController.platformTabPane.getTabs().add(tab);
//	}
//	
//	private void addTlsParameter() {
//		TlsParameterController controller = new TlsParameterController(tlsParameterModel);
//		Tab tab = new Tab();
//		tab.setText("TLS");
//		tab.setClosable(false);
//		tab.setContent(controller.parameterAnchorPane);
//		rootLayoutController.platformTabPane.getTabs().add(tab);
//	}
	
	private void instantiate() {
		deviceParameterModel = new DeviceParameterModel();
		if (Files.exists(Paths.get(Const.LIDAR_DEVICE_PARAMETER))) {
			deviceParameterModel.load(Const.LIDAR_DEVICE_PARAMETER);
		}
		
		beamParameterModel = new BeamParameterModel();
		if (Files.exists(Paths.get(Const.LIDAR_BEAM_PARAMETER))) {
			beamParameterModel.load(Const.LIDAR_BEAM_PARAMETER);
		}
		
		monoPulseParameterModel = new MonoPulseParameterModel();
		if (Files.exists(Paths.get(Const.LIDAR_MONO_PULSE_PARAMETER))) {
			monoPulseParameterModel.load(Const.LIDAR_MONO_PULSE_PARAMETER);
		}
		
		alsParameterModel = new AlsParameterModel();
		if (Files.exists(Paths.get(Const.LIDAR_ALS_PARAMETER))) {
			alsParameterModel.load(Const.LIDAR_ALS_PARAMETER);
		}
		
		tlsParameterModel = new TlsParameterModel();
		if (Files.exists(Paths.get(Const.LIDAR_TLS_PARAMETER))) {
			tlsParameterModel.load(Const.LIDAR_TLS_PARAMETER);
		}
		
		mlsParameterModel = new MlsParameterModel();
		if (Files.exists(Paths.get(Const.LIDAR_MLS_PARAMETER))) {
			mlsParameterModel.load(Const.LIDAR_MLS_PARAMETER);
		}
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

	public static void main(String[] args) {
		launch(args);
	}
}
