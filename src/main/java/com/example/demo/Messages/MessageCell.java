package com.example.demo.Messages;

import com.example.demo.Logic.Types;
import lombok.Data;

@Data
public class MessageCell {
    Types type = Types.UNREVEALED;
    int number;
    boolean isFlagged;
}
