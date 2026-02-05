package org.firstinspires.ftc.teamcode.DecodeChallenge.Paths;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierCurve;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;

public class BluePathLibrary {
    private final Follower _follower;

    public PathChain AlignToFirst;
    public PathChain IntakeFirstRow;

    public  PathChain ReturnFirstRow;

    public BluePathLibrary(Follower follower){
        _follower = follower;

        AlignToFirst = getAlignToFirstRow();
        IntakeFirstRow = getIntakeFirstRow();
        ReturnFirstRow = getReturnFirstRow();
    }

    private PathChain getAlignToFirstRow(){
        return _follower.pathBuilder().addPath(
                        new BezierCurve(
                                new Pose(60.506, 11.073),
                                new Pose(57.886, 37.997),
                                new Pose(44.613, 34.822)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(90), Math.toRadians(180))
                .build();
    }

    private PathChain getPathToIntakeRow1(){
        return  _follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(44.613, 34.822),
                                new Pose(17.183, 35.543)
                        )
                ).setTangentHeadingInterpolation()
                .build();
    }

    private PathChain getPathReturnFromRow1(){
        return _follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(17.183, 35.543),

                                new Pose(60.454, 10.723)
                        )
                ).setConstantHeadingInterpolation(Math.toRadians(110))
                .setReversed()
                .build();
    }
    private PathChain getAlignToSecondRow(){
        return  _follower.pathBuilder().addPath(
                        new BezierCurve(
                                new Pose(55.995, 7.909),
                                new Pose(60.020, 59.855),
                                new Pose(40.939, 59.853)
                        )
                ).setTangentHeadingInterpolation()
                .setReversed()
                .build();
    }

    private PathChain getPathToIntakeRow2(){
        return  _follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(40.939, 59.853),

                                new Pose(13.015, 60.431)
                        )
                ).setTangentHeadingInterpolation()
                .setReversed()
                .build();
    }

    private PathChain getPathReturnFromRow2() {
        return null;
    }

    private PathChain getPathToReturnFromRow2(){
        return  _follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(13.015, 60.431),

                                new Pose(55.970, 7.797)
                        )
                ).setTangentHeadingInterpolation()
                .setReversed()
                .build();
    }

    private PathChain getAlignToFirst(){
        return  _follower.pathBuilder().addPath(
                        new BezierCurve(
                                new Pose(55.970, 7.797),
                                new Pose(73.340, 84.183),
                                new Pose(43.391, 83.716)
                        )
                ).setTangentHeadingInterpolation()
                .setReversed()
                .build();

    }

    private PathChain getIntakeFirstRow(){
        return  _follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(43.391, 83.716),

                                new Pose(11.914, 84.051)
                        )
                ).setTangentHeadingInterpolation()
                .setReversed()
                .build();
    }

    private PathChain getReturnFirstRow() {
        return null;
    }

    public PathChain getPathToReturnFromRow3(){
        return  _follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(11.914, 84.051),

                                new Pose(56.249, 7.934)
                        )
                ).setTangentHeadingInterpolation()
                .setReversed()
                .build();
    }

    public PathChain getPathOutOfLaunchZone(){
        return  _follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(61.531, 6.361),

                                new Pose(61.861, 33.183)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(90), Math.toRadians(180))
                .setReversed()
                .build();
    }
}
