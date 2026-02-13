package org.firstinspires.ftc.teamcode.DecodeChallenge.Systems;

import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.DecodeChallenge.Controllers.DistanceSensorController;
import org.firstinspires.ftc.teamcode.DecodeChallenge.Controllers.IntakeController;
import org.firstinspires.ftc.teamcode.DecodeChallenge.Controllers.LaunchController;
import org.firstinspires.ftc.teamcode.DecodeChallenge.Controllers.ScooperController;

public class FireSequenceSystemStateMachine {
    public enum LaunchState {Idle, Initialize, BallSense, SpinningUp, Fire, Reset }
    public enum LaunchDistance {Far, Close}
    private LaunchState _currentState = LaunchState.Idle;
    private final Telemetry _telemetry;
    private final LaunchController _launcher;
    private final ScooperController _scooper;
    private final IntakeController _intake;
    private final DistanceSensorController _distanceSensor;
    private final ElapsedTime _stateTimer = new ElapsedTime();

    public  int _shotsRemaining = 0;

    private final double FAR_LAUNCH_VELOCITY = 2500;
    private final double CLOSE_LAUNCH_VELOCITY = 2000;

    private final double BALL_PRESENT_DISTANCE = 4.0;
    private final double SCOOP_UP_TIME = 0.8;
    private final double SCOOP_DOWN_TIME = 0.8;
    private final double MAX_TIME_OUT = 1.0;

    public FireSequenceSystemStateMachine(Telemetry telemetry, RobotMapping rc) {
        _telemetry = telemetry;
        _intake = new IntakeController(rc.UpperLeftIntake, rc.UpperRightIntake, rc.LowerLeftIntake, rc.LowerRightIntake);
        _launcher = new LaunchController(rc.Goat);
        _scooper = new ScooperController(rc.Scooper);
        _distanceSensor = new DistanceSensorController(rc.FrontColorSensor);
    }

    public void InitializeFar(){
        _launcher.StartVelocity(FAR_LAUNCH_VELOCITY);
        ChangeState(LaunchState.Initialize);
    }

    public void InitializeClose(){
        _launcher.StartVelocity(CLOSE_LAUNCH_VELOCITY);
        ChangeState(LaunchState.Initialize);
    }

    public boolean IsBusy(){
        return _currentState != LaunchState.Idle;
    }

    public void UpdateStatus() {
        _telemetry.addData("Fire State: ", _currentState);
        _distanceSensor.DebugOutuput(_telemetry);
        _launcher.ReportVelocity(_telemetry);

        switch (_currentState) {
            case Initialize:
                _shotsRemaining = 3;
                _scooper.ScoopDown();

                if (_shotsRemaining > 0 || _stateTimer.seconds() > MAX_TIME_OUT) {
                    ChangeState(LaunchState.BallSense);
                }
                break;

            case BallSense:
                if (_distanceSensor.GetDistanceCm() < BALL_PRESENT_DISTANCE || _stateTimer.seconds() > MAX_TIME_OUT) {
                    ChangeState(LaunchState.SpinningUp);
                }
                break;

            case SpinningUp:
                if (_launcher.IsAtFullSpeed() || _stateTimer.seconds() > MAX_TIME_OUT) {
                    ChangeState(LaunchState.Fire);
                }
                break;

            case Fire:
                _scooper.ScoopUp();
                _intake.Activate();

                if (_stateTimer.seconds() > SCOOP_UP_TIME) {
                    _shotsRemaining--;
                    _intake.Deactivate();

                    ChangeState(LaunchState.Reset);
                }
                break;

            case Reset:
                _scooper.ScoopDown();

                if (_stateTimer.seconds() > SCOOP_DOWN_TIME){
                    if (_shotsRemaining > 0){
                        ChangeState(LaunchState.BallSense);
                    } else {
                        _launcher.Stop();
                        ChangeState(LaunchState.Idle);
                    }
                }
                break;
        }
    }

    private void ChangeState(LaunchState newState){
        _currentState = newState;
        _stateTimer.reset();
    }
}
