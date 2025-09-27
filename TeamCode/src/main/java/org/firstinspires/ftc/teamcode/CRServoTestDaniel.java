
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;

@TeleOp(name="CR_SERVO_TEST_DANIEL", group="Linear OpMode")
public class CRServoTestDaniel extends LinearOpMode {
    private double currentPower = 0.0;

    @Override
    public void runOpMode() {
        CRServo[] servos = new CRServo[6];

        servos[0] = hardwareMap.get(CRServo.class, "zero");
        servos[1] = hardwareMap.get(CRServo.class, "one");
        servos[2] = hardwareMap.get(CRServo.class, "two");
        servos[3] = hardwareMap.get(CRServo.class, "three");
        servos[4] = hardwareMap.get(CRServo.class, "four");
        servos[5] = hardwareMap.get(CRServo.class, "five");

        // 0: inactive, 1: forward, -1: backward
        int[] servoDirections = {1, 1, 0, 0, 0, 0};

        int currentlySelected = 0;

        boolean UpLastPressed = false;
        boolean DownLastPressed = false;
        boolean RightBumperLastPressed = false;

        while(opModeInInit()) {
            if (gamepad1.dpad_down) {
                if (!DownLastPressed) {
                    currentlySelected = (currentlySelected + 1) % 6;
                    DownLastPressed = true;
                }
            } else {
                DownLastPressed = false;
            }

            if (gamepad1.dpad_up) {
                if (!UpLastPressed) {
                    currentlySelected = (currentlySelected - 1 + 6) % 6;
                    UpLastPressed = true;
                }
            } else {
                UpLastPressed = false;
            }

            // Toggle the currently selected servo's direction
            if (gamepad1.right_bumper) {
                if (!RightBumperLastPressed) {
                    servoDirections[currentlySelected] += 1;
                    if (servoDirections[currentlySelected] > 1) {
                        servoDirections[currentlySelected] = -1;
                    }
                    RightBumperLastPressed = true;
                }
            } else {
                RightBumperLastPressed = false;
            }

            // Print controls
            telemetry.addLine("Use D-Pad to select servo, Right Bumper to change direction");

            // Print info about the servos
            for (int i = 0; i < 6; i++) {
                String status;
                if (servoDirections[i] == 0) {
                    status = "inactive";
                } else if (servoDirections[i] == 1) {
                    status = "forward";
                } else {
                    status = "backward";
                }

                if (i == currentlySelected) {
                    telemetry.addData(">", "Servo %d: %s", i, status);
                } else {
                    telemetry.addData(" ", "Servo %d: %s", i, status);
                }
            }

            telemetry.update();
        }

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {
            // Adjust power
            if (gamepad1.a) {
                currentPower += 0.001;
            }
            if (gamepad1.b) {
                currentPower -= 0.001;
            }
            currentPower = Math.max(0.0, Math.min(1.0, currentPower));

            // Set servo powers
            for (int i = 0; i < 6; i++) {
                servos[i].setPower(servoDirections[i] * currentPower);
            }

            telemetry.addData("Power", "%.2f", currentPower);
            telemetry.addLine("Press A to increase power, B to decrease power");
            telemetry.update();
        }
    }
}
