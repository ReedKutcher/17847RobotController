package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp

public class BasicDrive extends LinearOpMode {
    private DcMotor fl;
    private DcMotor fr;
    private DcMotor bl;
    private DcMotor br;

    double pwrX;
    double pwrY;
    double turn;

    @Override
    public void runOpMode() {
        fl = hardwareMap.get(DcMotor.class, "fl");
        fr = hardwareMap.get(DcMotor.class, "fr");
        bl = hardwareMap.get(DcMotor.class, "bl");
        br = hardwareMap.get(DcMotor.class, "br");

        waitForStart();

        while (opModeIsActive()){
            pwrX = gamepad1.left_stick_x;
            pwrY = gamepad1.left_stick_y;
            turn = gamepad1.right_stick_x;

            fl.setPower(-pwrX + pwrY - turn);
            fr.setPower(-pwrX - pwrY - turn);
            bl.setPower(pwrX + pwrY - turn);
            br.setPower(pwrX - pwrY - turn);
        }
    }
}
