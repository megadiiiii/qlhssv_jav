package org.example.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Reward {
    String rewardId;
    String studentId;
    String rewardNote;
    Date rewardDate;
    String rewardQD;
}
