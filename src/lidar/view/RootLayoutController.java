package lidar.view;

import javafx.fxml.FXML;
import javafx.scene.control.TabPane;
import javafx.scene.layout.VBox;
import lidar.MainApp;
import utils.Const;

public class RootLayoutController {
	@FXML
	public VBox generalParameterVBox;
	@FXML
	public TabPane platformTabPane;
	
	private MainApp mainApp;
	
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}
	
	@FXML
	private void handleSave() {
		mainApp.deviceParameterModel.save(Const.LIDAR_DEVICE_PARAMETER);
		mainApp.beamParameterModel.save(Const.LIDAR_BEAM_PARAMETER);
		mainApp.monoPulseParameterModel.save(Const.LIDAR_MONO_PULSE_PARAMETER);
		mainApp.alsParameterModel.save(Const.LIDAR_ALS_PARAMETER);
		mainApp.tlsParameterModel.save(Const.LIDAR_TLS_PARAMETER);
		mainApp.mlsParameterModel.save(Const.LIDAR_MLS_PARAMETER);
	}
}
