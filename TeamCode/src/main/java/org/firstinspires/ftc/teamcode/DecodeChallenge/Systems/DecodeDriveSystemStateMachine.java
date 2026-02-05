//package org.firstinspires.ftc.teamcode.DecodeChallenge.Systems;
//
//import com.pedropathing.follower.Follower;
//import com.pedropathing.geometry.Pose;
//
//import org.firstinspires.ftc.robotcore.external.Telemetry;
//import org.firstinspires.ftc.teamcode.DecodeChallenge.AllianceColor;
//import org.firstinspires.ftc.teamcode.DecodeChallenge.AprilTagConstant;
//import org.firstinspires.ftc.teamcode.DecodeChallenge.Controllers.AprilTagDetectionController;
//import org.firstinspires.ftc.teamcode.DecodeChallenge.Controllers.CameraController;
//import org.firstinspires.ftc.teamcode.DecodeChallenge.Paths.BluePathLibrary;
//import org.firstinspires.ftc.teamcode.DecodeChallenge.Paths.PathLibrary;
//import org.firstinspires.ftc.teamcode.DecodeChallenge.Paths.RedPathLibrary;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class DecodeDriveSystemStateMachine {
//    public enum DriveState {DecidingNextSpecimen, Scan, DriveToRow, WaitForLoadTrigger, LoadingSpecimens, LoadingComplete, DriveToLaunch, ReadyToFire, Idle }
//    private final Telemetry _telemetry;
//    private final Follower _follower;
//    private final AprilTagDetectionController _camera;
//    private final CameraController _cameraController;
//    private final AllianceColor _allianceColor;
//    private PathLibrary _paths;
//
//    private List<Integer> _completedSpecimens = new ArrayList<>();
//    private int _targetSpecimenId;
//    private DriveState _currentDriveState = DriveState.Idle;
//
//    private boolean _loadTriggerPulled = false;
//    private boolean _returnTriggerPulled = false;
//
//    public DecodeDriveSystemStateMachine(Telemetry telemetry, Follower follower, RobotMapping rm, AllianceColor allianceColor) {
//        _telemetry = telemetry;
//        _follower = follower;
//        _allianceColor = allianceColor;
//        _camera = new AprilTagDetectionController();
//        _cameraController = new CameraController(_telemetry, rm.Webcam, _allianceColor);
//
//        if (_allianceColor == AllianceColor.Blue){
//            _paths = new BluePathLibrary(_follower);
//        } else {
//            _paths = new RedPathLibrary(_follower);
//        }
//    }
//
//    public void Init(){
//        long startTime = System.currentTimeMillis();
//        Pose startPosition = null;
//
//        while (startPosition == null && (System.currentTimeMillis() - startTime) < 3000) {
//            startPosition = _cameraController.GetRobotPose();
//
//            try {
//                Thread.sleep(50);
//            } catch (InterruptedException e) {}
//        }
//
//        if (startPosition == null) {
//            startPosition = (_allianceColor == AllianceColor.Blue)
//                    ? new Pose(60.506, 11.073)
//                    : new Pose(82.631, 11.471, Math.toRadians(180));
//            _telemetry.addLine("Camera Timeout: Using Default Pose");
//        } else {
//            _telemetry.addLine("Camera Initialized: Pose Locked");
//        }
//
//        _follower.setStartingPose(startPosition);
//        _telemetry.update();
//    }
//    public void ScanNextSpecimen(){
//        MarkSpecimentComplete(_targetSpecimenId);
//
//        ChangeState(DriveState.DecidingNextSpecimen);
//    }
//    public void StartLoading(){
//        _loadTriggerPulled = true;
//    }
//    public void ReturnToLaunch(){
//        _returnTriggerPulled = true;
//    }
//
//    public boolean IsWaitingToLoad(){
//        return _currentDriveState == DriveState.WaitForLoadTrigger;
//    }
//    public boolean IsLoadComplete(){
//        return _currentDriveState == DriveState.LoadingComplete;
//    }
//    public boolean IsReadyToFire(){
//        return _currentDriveState == DriveState.ReadyToFire;
//    }
//
//    private int IdentifyNextSpecimen(){
//
//        if (_completedSpecimens.size() == 0) {
//            return AprilTagConstant.GPP;
//        }
//
//        if (_completedSpecimens.size() == 1){
//            return AprilTagConstant.PGP;
//        }
//
//        if (_completedSpecimens.size() == 2){
//            return AprilTagConstant.PPG;
//        }
//
//        return -1;
//    }
//    private void MarkSpecimentComplete(int specimenId){
//        _completedSpecimens.add(specimenId);
//    }
//
//    private void ChangeState(DriveState newState){
//        _currentDriveState = newState;
//        // NOTE: If you need to do something when the state changes, do it here:
//    }
//
//    //Navigation Logic
//    public void SetRowDestination(int id){
//        switch (id){
//            case AprilTagConstant.GPP:
//                _follower.followPath(_paths.getAlignToFirstRow());
//                break;
//
//            case AprilTagConstant.PGP:
//                _follower.followPath(_paths.getAlignToSecondRow());
//                break;
//
//            case AprilTagConstant.PPG:
//                _follower.followPath(_paths.getAlignToThirdRow());
//                break;
//
//            default:
//                ChangeState(DriveState.Idle);
//                break;
//        }
//    }
//
//    public void SetIntakeRoute(int id){
//        switch (id){
//            case AprilTagConstant.GPP:
//                _follower.followPath(_paths.getPathToIntakeRow1());
//                break;
//
//            case AprilTagConstant.PGP:
//                _follower.followPath(_paths.getPathToIntakeRow2());
//                break;
//
//            case AprilTagConstant.PPG:
//                _follower.followPath(_paths.getPathToIntakeRow3());
//        }
//    }
//
//    public void SetReturnRoute(int id){
//        switch (id){
//            case AprilTagConstant.GPP:
//                _follower.followPath(_paths.getPathReturnFromRow1());
//                break;
//
//            case AprilTagConstant.PGP:
//                _follower.followPath(_paths.getPathReturnFromRow2());
//                break;
//
//            case AprilTagConstant.PPG:
//                _follower.followPath(_paths.getPathReturnFromRow3());
//        }
//    }
//
//    public DriveState GetState(){
//        _telemetry.addData("DriveState: ", _currentDriveState);
//        _telemetry.addData("Completed Specimens: ", _completedSpecimens);
//
//        switch (_currentDriveState){
//
//            case Scan:
//                _camera.Update();
//
//                int targetBasket = _camera.GetAprilTag();
//
//                if (targetBasket == -1){
//                    _telemetry.addData("Target Found!", targetBasket);
//                    ChangeState(DriveState.DecidingNextSpecimen);
//                }
//                break;
//
//            case DecidingNextSpecimen:
//                _targetSpecimenId = IdentifyNextSpecimen();
//
//                if (_targetSpecimenId != 1){
//                    SetRowDestination(_targetSpecimenId);
//                    ChangeState(DriveState.DriveToRow);
//                } else {
//                    ChangeState(DriveState.Idle);
//                }
//
//            case DriveToRow:
//                if(!_follower.isBusy()){
//                    ChangeState(DriveState.WaitForLoadTrigger);
//                }
//                break;
//
//            case WaitForLoadTrigger:
//
//                if (_loadTriggerPulled){
//                    SetIntakeRoute(_targetSpecimenId);
//                    ChangeState(DriveState.LoadingSpecimens);
//                    _loadTriggerPulled = false;
//                }
//                break;
//
//            case LoadingSpecimens:
//                if (_returnTriggerPulled){
//                    SetReturnRoute(_targetSpecimenId);
//                    ChangeState(DriveState.DriveToLaunch);
//                    _returnTriggerPulled = false;
//                }
//                break;
//
//            case DriveToLaunch:
//                if (!_follower.isBusy()){
//                    ChangeState(DriveState.ReadyToFire);
//                }
//                break;
//
//            case Idle:
//                if(!_follower.isBusy()){
//                    _follower.holdPoint(_follower.getPose());
//                }
//                break;
//        }
//
//        return _currentDriveState;
//    }
//}