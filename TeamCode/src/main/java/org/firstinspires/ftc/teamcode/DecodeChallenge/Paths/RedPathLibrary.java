//package org.firstinspires.ftc.teamcode.DecodeChallenge.Paths;
//
//import com.pedropathing.follower.Follower;
//import com.pedropathing.geometry.BezierCurve;
//import com.pedropathing.geometry.BezierLine;
//import com.pedropathing.geometry.Pose;
//import com.pedropathing.paths.PathChain;
//
//public class RedPathLibrary implements PathLibrary {
//    private final Follower _follower;
//
//    public RedPathLibrary(Follower follower){
//        _follower = follower;
//    }
//
//    public PathChain getAlignToFirstRow(){
//        return _follower.pathBuilder().addPath(
//                        new BezierCurve(
//                                new Pose(85.292, 11.277),
//                                new Pose(84.230, 35.866),
//                                new Pose(101.145, 35.367)
//                        )
//                ).setTangentHeadingInterpolation()
//
//                .build();
//    }
//    public PathChain getPathToIntakeRow1(){
//        return _follower.pathBuilder().addPath(
//                        new BezierLine(
//                                new Pose(101.145, 35.367),
//
//                                new Pose(132.397, 35.114)
//                        )
//                ).setTangentHeadingInterpolation()
//
//                .build();
//    }
//
//    @Override
//    public PathChain getPathReturnFromRow1() {
//        return null;
//    }
//
//    public PathChain getPathToReturnFromRow1(){
//        return _follower.pathBuilder().addPath(
//                        new BezierLine(
//                                new Pose(132.397, 35.114),
//
//                                new Pose(85.762, 11.248)
//                        )
//                ).setConstantHeadingInterpolation(Math.toRadians(60))
//                .setReversed()
//                .build();
//    }
//    public PathChain getAlignToSecondRow(){
//        return _follower.pathBuilder().addPath(
//                        new BezierCurve(
//                                new Pose(85.762, 11.248),
//                                new Pose(78.974, 59.814),
//                                new Pose(103.370, 60.185)
//                        )
//                ).setTangentHeadingInterpolation()
//
//                .build();
//    }
//    public PathChain getPathToIntakeRow2(){
//        return _follower.pathBuilder().addPath(
//                        new BezierLine(
//                                new Pose(103.370, 60.185),
//
//                                new Pose(134.775, 59.415)
//                        )
//                ).setTangentHeadingInterpolation()
//
//                .build();
//    }
//
//    @Override
//    public PathChain getPathReturnFromRow2() {
//        return null;
//    }
//
//    public PathChain getPathToReturnFromRow2(){
//        return _follower.pathBuilder().addPath(
//                        new BezierLine(
//                                new Pose(134.775, 59.415),
//
//                                new Pose(85.212, 11.676)
//                        )
//                ).setConstantHeadingInterpolation(Math.toRadians(60))
//
//                .build();
//    }
//    public PathChain getAlignToThirdRow(){
//        return _follower.pathBuilder().addPath(
//                        new BezierCurve(
//                                new Pose(85.212, 11.676),
//                                new Pose(74.987, 80.256),
//                                new Pose(102.356, 83.229)
//                        )
//                ).setTangentHeadingInterpolation()
//
//                .build();
//    }
//    public PathChain getPathToIntakeRow3(){
//        return _follower.pathBuilder().addPath(
//                        new BezierLine(
//                                new Pose(102.356, 83.229),
//
//                                new Pose(132.100, 83.516)
//                        )
//                ).setTangentHeadingInterpolation()
//
//                .build();
//    }
//
//    @Override
//    public PathChain getPathReturnFromRow3() {
//        return null;
//    }
//
//    public PathChain getPathToReturnFromRow3(){
//        return _follower.pathBuilder().addPath(
//                        new BezierLine(
//                                new Pose(132.100, 83.516),
//
//                                new Pose(85.629, 11.681)
//                        )
//                ).setConstantHeadingInterpolation(Math.toRadians(60))
//
//                .build();
//    }
//    public PathChain getPathOutOfLaunchZone(){
//        return _follower.pathBuilder().addPath(
//                        new BezierLine(
//                                new Pose(85.629, 11.681),
//
//                                new Pose(85.752, 32.259)
//                        )
//                ).setTangentHeadingInterpolation()
//
//                .build();
//    }
//}
