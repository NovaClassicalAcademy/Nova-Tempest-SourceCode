package org.firstinspires.ftc.teamcode.DecodeChallenge.PedroPathing;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierCurve;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;

public class RedFullRunPathing {
    public PathChain PreloadedShoot;
    public PathChain FirstRowAlign;
    public PathChain FirstRowLoad;
    public PathChain FirstRowShoot;
    public PathChain SecondRowAlign;
    public PathChain SecondRowLoad;
    public PathChain SecondRowShoot;
    public PathChain ThirdRowAlign;
    public PathChain ThirdRowLoad;
    public PathChain ThirdRowShoot;
    public PathChain FinishPosition;

    public RedFullRunPathing(Follower follower) {
        InitPathChain(follower);
        follower.setStartingPose(new Pose(80.0, 8.000, Math.toRadians(270)));
    }

    private void InitPathChain(Follower follower){

        PreloadedShoot = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(80.000, 8.000),
                                new Pose(81.000, 25.000)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(270), Math.toRadians(245))
                .build();

        FirstRowAlign = follower.pathBuilder().addPath(
                        new BezierCurve(
                                new Pose(81.000, 25.000),
                                new Pose(86.000, 33.000),
                                new Pose(100.000, 33.000)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(290), Math.toRadians(180))
                .build();

        FirstRowLoad = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(100.000, 33.000),
                                new Pose(130.000, 33.000)
                        )
                ).addParametricCallback(0.0, ()-> follower.setMaxPower(0.25))
                .setConstantHeadingInterpolation(Math.toRadians(180))
                .build();

        FirstRowShoot = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(130.000, 33.000),
                                new Pose(81.000, 25.000)
                        )
                ).addParametricCallback(0.0, ()-> follower.setMaxPower(1))
                .setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(290))
                .build();

        SecondRowAlign = follower.pathBuilder().addPath(
                        new BezierCurve(
                                new Pose(81.000, 25.000),
                                new Pose(80.000, 57.000),
                                new Pose(102.000, 57.000)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(290), Math.toRadians(180))
                .build();

        SecondRowLoad = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(102.000, 57.000),
                                new Pose(130.000, 57.000)
                        )
                ).addParametricCallback(0.1, ()-> follower.setMaxPower(0.25))
                .setConstantHeadingInterpolation(Math.toRadians(180))
                .build();

        SecondRowShoot = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(130.000, 57.000),
                                new Pose(81.000, 25.000)
                        )
                ).addParametricCallback(0.0, ()-> follower.setMaxPower(1))
                .setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(290))
                .build();

        ThirdRowAlign = follower.pathBuilder().addPath(
                        new BezierCurve(
                                new Pose(81.000, 25.000),
                                new Pose(79.000, 80.000),
                                new Pose(105.000, 80.000)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(290), Math.toRadians(180))
                .build();

        ThirdRowLoad = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(105.000, 80.000),
                                new Pose(130.000, 80.000)
                        )
                ).addParametricCallback(0.1, ()-> follower.setMaxPower(0.25))
                .setConstantHeadingInterpolation(Math.toRadians(180))
                .build();

        ThirdRowShoot = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(130.000, 80.000),
                                new Pose(81.000, 25.000)
                        )
                ).addParametricCallback(0.0, ()-> follower.setMaxPower(1))
                .setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(290))
                .build();

        FinishPosition = follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(81.000, 25.000),
                                new Pose(81.000, 38.000)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(290), Math.toRadians(270))

                .build();
    }
}
