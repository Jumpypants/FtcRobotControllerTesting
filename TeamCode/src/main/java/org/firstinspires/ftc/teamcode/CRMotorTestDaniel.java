/* Copyright (c) 2021 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.firstinspires.ftc.teamcode;

import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;


@TeleOp(name="CR_MOTOR_TEST_DANIEL", group="Linear OpMode")
public class CRMotorTestDaniel extends LinearOpMode {
    private double currentPower = 0.0;

    @Override
    public void runOpMode() {
        Motor[] motors = new Motor[4];

        motors[0] = new Motor(hardwareMap, "zero");
        motors[1] = new Motor(hardwareMap, "one");
        motors[2] = new Motor(hardwareMap, "two");
        motors[3] = new Motor(hardwareMap, "three");

        for (Motor m : motors) {
            m.setRunMode(Motor.RunMode.VelocityControl);
        }

        // 0: inactive, 1: forward, -1: backward
        int[] motorDirections = {0, 0, 0, 0};

        int currentlySelected = 0;

        boolean UpLastPressed = false;
        boolean DownLastPressed = false;
        boolean RightBumperLastPressed = false;

        while(opModeInInit()) {
            if (gamepad1.dpad_down) {
                if (!DownLastPressed) {
                    currentlySelected = (currentlySelected + 1) % 4;
                    DownLastPressed = true;
                }
            } else {
                DownLastPressed = false;
            }

            if (gamepad1.dpad_up) {
                if (!UpLastPressed) {
                    currentlySelected = (currentlySelected - 1 + 4) % 4;
                    UpLastPressed = true;
                }
            } else {
                UpLastPressed = false;
            }

            // Toggle the currently selected motor's direction
            if (gamepad1.right_bumper) {
                if (!RightBumperLastPressed) {
                    motorDirections[currentlySelected] += 1;
                    if (motorDirections[currentlySelected] > 1) {
                        motorDirections[currentlySelected] = -1;
                    }
                    RightBumperLastPressed = true;
                }
            } else {
                RightBumperLastPressed = false;
            }

            // Print controls
            telemetry.addLine("Use D-Pad to select motor, Right Bumper to change direction");

            // Print info about the motors
            for (int i = 0; i < 4; i++) {
                String status;
                if (motorDirections[i] == 0) {
                    status = "inactive";
                } else if (motorDirections[i] == 1) {
                    status = "forward";
                } else {
                    status = "backward";
                }

                if (i == currentlySelected) {
                    telemetry.addData(">", "Motor %d: %s", i, status);
                } else {
                    telemetry.addData(" ", "Motor %d: %s", i, status);
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
            currentPower = Math.max(-1.0, Math.min(1.0, currentPower));

            // Set motor powers
            for (int i = 0; i < 4; i++) {
                motors[i].set(motorDirections[i] * currentPower);
            }

            telemetry.addData("Power", "%.2f", currentPower);
            telemetry.addLine("Press A to increase power, B to decrease power");
            telemetry.update();
        }
    }
}

