package org.firstinspires.ftc.teamcode.DecodeChallenge.Systems;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierCurve;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;

public class DecodePathing {

    public enum PathState { AtLaunchPosition, Travelling, AtLine1, AtLine2, AtLine3, Loading, Idle }

    private final Follower _follower;

    private PathChain _activePath;
    private PathState _currentState = PathState.Idle;

    public DecodePathing(Follower follower) {

        _follower = follower;
    }

    public PathChain GetPathToFirstRow(){
        return _follower.pathBuilder().addPath(
                new BezierCurve(
                        new Pose(56.000, 8.000),
                        new Pose(62.814, 32.989),
                        new Pose(40.432, 35.385)))
                .setLinearHeadingInterpolation(Math.toRadians(90), Math.toRadians(180))
                .build();
    }

    public PathChain GetPathToLoadFirstRow(){
        return _follower.pathBuilder().addPath(
                new BezierLine(
                        new Pose(40.432, 35.385),
                        new Pose(15.417, 35.417)))
                .setTangentHeadingInterpolation()
                .build();
    }

    public PathChain GetPathToLaunchPosition(){
        return _follower.pathBuilder().addPath(
                new BezierCurve(
                        new Pose(15.417, 35.417),
                        new Pose(53.737, 35.704),
                        new Pose(59.596, 17.351)))
                .setTangentHeadingInterpolation()
                .setReversed()
                .build();
    }

    public void SetNewPath(PathChain newPath){
        _activePath = newPath;
        _currentState = PathState.Travelling;
    }

    public PathState UpdatePathState(){

        // TODO: perform movement with switch statement
        switch (_currentState){

            case AtLaunchPosition:
                _currentState = PathState.Idle;
                break;

            case AtLine1:
                _currentState = PathState.Travelling;
                break;

            case AtLine2:
                _currentState = PathState.Travelling;
                break;

        }

        return _currentState;
    }
}