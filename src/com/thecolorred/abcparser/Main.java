package com.thecolorred.abcparser;
import java.io.*;
import javax.sound.midi.*;
import com.thecolorred.abcparser.parser.*;
public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Synthesizer d = null;
		Receiver r = null;
		Sequencer s = null;
		try {
			d = MidiSystem.getSynthesizer();
			if (!d.isOpen()) {
				d.open();
			}
			/**
			ShortMessage m = new ShortMessage();
			ShortMessage m2 = new ShortMessage();
			m.setMessage(ShortMessage.PROGRAM_CHANGE, 0, 19, 0);
			m2.setMessage(ShortMessage.PROGRAM_CHANGE, 1, 4, 0);
			r = d.getReceiver();
			r.send(m, -1);
			r.send(m2, -1);
			m.setMessage(ShortMessage.NOTE_ON, 0, 60, 80);
			m2.setMessage(ShortMessage.NOTE_ON, 1, 64, 100);
			r.send(m, -1);
			r.send(m2, -1);
			Thread.sleep(1000);
			m2.setMessage(ShortMessage.NOTE_OFF, 1, 64, 80);
			r.send(m2, -1);
			m2.setMessage(ShortMessage.NOTE_ON, 1, 68, 100);
			r.send(m2, -1);
			Thread.sleep(1000);
			m.setMessage(ShortMessage.NOTE_OFF, 0, 60, 80);
			m2.setMessage(ShortMessage.NOTE_OFF, 1, 68, 100);
			r.send(m2, -1);
			r.send(m, -1);
			m.setMessage(ShortMessage.NOTE_ON, 0, 62, 80);
			m2.setMessage(ShortMessage.NOTE_ON, 1, 66, 100);
			r.send(m, -1);
			r.send(m2, -1);
			Thread.sleep(1000);
			m2.setMessage(ShortMessage.NOTE_OFF, 1, 66, 100);
			r.send(m2, -1);
			m2.setMessage(ShortMessage.NOTE_ON, 1, 70, 100);
			r.send(m2, -1);
			Thread.sleep(1000);
			m.setMessage(ShortMessage.NOTE_OFF, 0, 62, 80);
			m2.setMessage(ShortMessage.NOTE_OFF, 1, 70, 100);
			r.send(m, -1);
			**/
			s = MidiSystem.getSequencer();
			s.open();
			Sequence e = new Sequence(Sequence.PPQ, 16);
			Track t = e.createTrack();
			long ticks = 0;
			for (Node n : Parser.parse("%abc\n[r:test]|D/2D/2da||z/2_aGFD/2F/2G/2|%test\n|C/2C/2da||z/2_aGFD/2F/2G/2|\n|B/2B/2da||z/2_aGFD/2F/2G/2|\n|_B/2_B/2da||z/2_aGFD/2F/2G/2|").contents) {
				if (n.type == Node.NodeType.NOTE) {
					if (n.note != -1) {
						t.add(new MidiEvent(new ShortMessage(ShortMessage.NOTE_ON, 0, n.note, 127), ticks));
						ticks += n.length * 16f;
						t.add(new MidiEvent(new ShortMessage(ShortMessage.NOTE_OFF, 0, n.note, 127), ticks));
					} else {
						ticks += n.length * 16f;
					}
				}
			}
			s.setLoopCount(Sequencer.LOOP_CONTINUOUSLY);
			s.setSequence(e);
			s.start();
			//Sequencer s = MidiSystem.getSequencer();
			//s.open();
			//Sequence e = MidiSystem.getSequence(new File("D:\\TragedyMIDI.mid"));
			//s.setSequence(e);
			//s.setLoopCount(Sequencer.LOOP_CONTINUOUSLY);
			//s.start();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (r != null) {
				r.close();
			}
			if (d != null) {
				d.close();
			}
			//} if (s != null) {
			//	s.close();
			//}
		}
		return;
	}
}