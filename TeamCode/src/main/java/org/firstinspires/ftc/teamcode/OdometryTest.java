package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@Autonomous

public class OdometryTest extends LinearOpMode {
    //define motors
    private DcMotor fl;
    private DcMotor fr;
    private DcMotor bl;
    private DcMotor br;

    private DcMotor odoLeft;
    private DcMotor odoRight;
    private DcMotor odoFront;

    //define variables

    double posXField;
    double posYField;
    double rotationField;

    int odoLeftPosInitial;
    int odoRightPosInitial;
    int odoFrontPosInitial;

    double odoLRDist = 50;
    double odoFDist = 25;

    //cmPerTick = (2 * pi * wheelRadius) / pulsesPerRevolution
    double cmPerTick = (2 * Math.PI * 1.6) / 2000;

    @Override
    public void runOpMode() {
        fl = hardwareMap.get(DcMotor.class, "fl");
        fl.setDirection(DcMotorSimple.Direction.FORWARD);
        fl.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        fr = hardwareMap.get(DcMotor.class, "fr");
        fr.setDirection(DcMotorSimple.Direction.FORWARD);
        fr.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        bl = hardwareMap.get(DcMotor.class, "bl");
        bl.setDirection(DcMotorSimple.Direction.FORWARD);
        bl.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        br = hardwareMap.get(DcMotor.class, "br");
        br.setDirection(DcMotorSimple.Direction.FORWARD);
        br.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        odoLeft = fl;
        odoRight = fr;
        odoFront = bl;

        waitForStart();

        odoLeftPosInitial = odoLeft.getCurrentPosition();
        odoRightPosInitial = odoRight.getCurrentPosition();
        odoFrontPosInitial = odoFront.getCurrentPosition();

        while (opModeIsActive()) {
            telemetry.addData("X Position: ", posXField);
            telemetry.addData("Y Position: ", posYField);
            telemetry.addData("Theta: ", rotationField);
            telemetry.update();
            updatePosition();
        }
    }

    public void updatePosition() {
        int odoLeftPosCurrent = odoLeft.getCurrentPosition();
        int odoRightPosCurrent = odoRight.getCurrentPosition();
        int odoFrontPosCurrent = odoFront.getCurrentPosition();

        int dTicksLeft = odoLeftPosCurrent - odoLeftPosInitial;
        int dTicksRight = odoRightPosCurrent - odoRightPosInitial;
        int dTicksFront = odoFrontPosCurrent - odoFrontPosInitial;

        double dxRobot = cmPerTick * (dTicksFront - odoFDist * (double)(dTicksRight - dTicksLeft) / odoLRDist);
        double dyRobot = cmPerTick * ((double)(dTicksLeft + dTicksRight) / 2);
        double dthetaRobot = cmPerTick * ((double)(dTicksRight - dTicksLeft) / odoLRDist);

        posXField += dxRobot * Math.cos(rotationField) - dyRobot * Math.sin(rotationField);
        posYField += dxRobot * Math.sin(rotationField) + dyRobot * Math.sin(rotationField);
        rotationField += dthetaRobot;

        odoLeftPosInitial = odoLeftPosCurrent;
        odoRightPosInitial = odoRightPosCurrent;
        odoFrontPosInitial = odoFrontPosCurrent;
    }
}
