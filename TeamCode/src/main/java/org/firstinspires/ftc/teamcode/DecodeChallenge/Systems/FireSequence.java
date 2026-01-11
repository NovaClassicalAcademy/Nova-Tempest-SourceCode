package org.firstinspires.ftc.teamcode.DecodeChallenge.Systems;

import org.firstinspires.ftc.teamcode.DecodeChallenge.Controllers.ColorSensorController;
import org.firstinspires.ftc.teamcode.DecodeChallenge.Controllers.IntakeController;
import org.firstinspires.ftc.teamcode.DecodeChallenge.Controllers.CannonController;
import org.firstinspires.ftc.teamcode.DecodeChallenge.Controllers.ScooperController;

public class FireSequence {

    public enum LaunchState { Off, SpinningUp, ScooperUp, Loading, BallSense, ReadyToLaunch, FireAway }

    private CannonController _launcher;
    private ScooperController _scooper;
    private IntakeController _intake;
    private ColorSensorController _colorSensor;
    private double _targetRPM = 200.00; // TODO: find max and min rpm
    private double _maxRPM = _targetRPM + 200;
    private LaunchState _currentState;
    private int _ballsFired;

    public FireSequence(RobotMapping rc) {
        _intake = new IntakeController(rc.UpperLeftIntake, rc.UpperRightIntake, rc.LowerLeftIntake, rc.LowerRightIntake);
        _launcher = new CannonController(rc.Goat);
        _scooper = new ScooperController(rc.Scooper);
        _colorSensor = new ColorSensorController(rc.ColorSensor);
    }

    public void InitializeCountDown(){
        _launcher.SpinUp();
        _currentState = LaunchState.SpinningUp;
        _ballsFired = 3;

        _launcher.SetRPM(_targetRPM, _maxRPM);
    }

    public void FireAway(){
        _currentState = LaunchState.FireAway;
    }
    public void LoadCannon(){
        _currentState = LaunchState.Loading;
        _intake.Activate();
    }

    public boolean IsLoaded(){
        return _currentState == LaunchState.ReadyToLaunch;
    }

    public LaunchState GetStatus() {

        if (_ballsFired == 3) {
            _currentState = LaunchState.Off;
        }

        switch (_currentState) {

            case SpinningUp:
                if (_launcher.IsReadyForLaunch()) {
                    _currentState = LaunchState.ReadyToLaunch;
                }
                break;

            case FireAway:
                _scooper.ScoopUp();
                _currentState = LaunchState.ScooperUp;
                _ballsFired++;
                break;

            case ScooperUp:
                if (_scooper.IsUp()) {
                    _intake.Activate();
                    _scooper.ScoopDown();
                    _currentState = LaunchState.BallSense;
                }
                break;

            case BallSense:
                if (_colorSensor.IsBallIdentified() && _scooper.IsDown()) {
                    _currentState = LaunchState.ReadyToLaunch;
                }
                break;
        }

        return _currentState;
    }

    public boolean IsLaunchComplete(){
        return _currentState == LaunchState.ReadyToLaunch;
    }


}
