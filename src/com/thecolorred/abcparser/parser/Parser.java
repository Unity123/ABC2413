package com.thecolorred.abcparser.parser;
import java.util.*;
import java.util.regex.*;

public class Parser {
	public static ABCDocument parse(String data) throws Exception {
		ABCDocument d = new ABCDocument();
		d.contents = new ArrayList<Node>();
		if (!(data.contains("%abc"))) {
			throw new Exception("Not ABC data!");
		} else {
			d.version = data.substring(0, data.indexOf("\n"));
		}
		data = data.substring(data.indexOf("\n")+1);
		data = data.replaceAll("\\[r:.*\\]|%.*", "");
		System.out.println(data);
		Pattern match = Pattern.compile("([\\^=_]*)([A-Ga-gz](?!:))([',]*)(\\/?\\d*)");
		Matcher m2 = match.matcher(data);
		Matcher m3 = Pattern.compile("([A-Z]):(.+)").matcher(data);
		List<List<String>> matches = new ArrayList<List<String>>();
		while (m2.find() || m3.find()) {
			if (m3.find()) {
				List<String> l = new ArrayList<String>();
				matches.add(l);
				for (int i = 1; i <= m3.groupCount(); i++) {
					l.add(m3.group(i));
				}
				l.add("attr");
				l.add(Integer.toString(m3.start()));
			}
			if (m2.find()) {
				List<String> l = new ArrayList<String>();
				matches.add(l);
				for (int i = 1; i <= m2.groupCount(); i++) {
					l.add(m2.group(i));
				}
				l.add("note");
				l.add(Integer.toString(m2.start()));
			}
			//System.out.println(m2.group());
		}
		//match = Pattern.compile("");
		//m2 = match.matcher(data);
		//while (m2.find()) {
		//	List<String> l = new ArrayList<String>();
		//	matches.add(l);
		//	for (int i = 1; i <= m2.groupCount(); i++) {
		//		l.add(m2.group(i));
		//	}
		//	l.add("attr");
		//	//System.out.println(m2.group());
		//}
		for (List<String> s : matches) {
			Node n = new Node();
			if (s.contains("note")) {
			n.type = Node.NodeType.NOTE;
			n.instrument = 0;
			String q = s.get(1);
			switch (q) {
			case "A":
				n.note = 57;
				break;
			case "B":
				n.note = 59;
				break;
			case "C":
				n.note = 60;
				break;
			case "D":
				n.note = 62;
				break;
			case "E":
				n.note = 64;
				break;
			case "F":
				n.note = 65;
				break;
			case "G":
				n.note = 67;
				break;
			case "a":
				n.note = 69;
				break;
			case "b":
				n.note = 71;
				break;
			case "c":
				n.note = 72;
				break;
			case "d":
				n.note = 74;
				break;
			case "e":
				n.note = 76;
				break;
			case "f":
				n.note = 77;
				break;
			case "g":
				n.note = 79;
				break;
			case "z":
				n.note = -1;
				break;
			default:
				throw new Exception("Something went horribly wrong during the parsing process, consider reporting a bug with Java's regex functionality");
			}
			byte sharpFlat = 0;
			for (int i = 0; i < s.get(0).length(); i++) {
					switch (s.get(0).charAt(i)) {
					case '^':
						sharpFlat++;
						break;
					case '=':
						break;
					case '_':
						sharpFlat--;
					break;
				default:
						throw new Exception("Something went horribly wrong during the parsing process, consider reporting a bug with Java's regex functionality");
				}
			}
			n.note += sharpFlat;
			byte octave = 0;
				for (int i = 0; i < s.get(2).length(); i++) {
					switch (s.get(2).charAt(i)) {
					case '\'':
						octave++;
						break;
					case ',':
						octave--;
						break;
					default:
						throw new Exception("Something went horribly wrong during the parsing process, consider reporting a bug with Java's regex functionality");
					}
				}
			n.note += 12*octave;
			n.length = 1;
				if (s.get(3).contains("/")) {
					n.length = (1f/Integer.parseInt(s.get(3).substring(1)));
				} else if (s.get(3).length() > 0) {
					n.length = Integer.parseInt(s.get(3));
				}
			} else if (s.contains("attr")) {
				n.type = Node.NodeType.FIELD;
				n.fieldName = s.get(0);
				n.text = s.get(1);
			}
			n.sortIndex = Integer.parseInt(s.get(s.size()-1));
			d.contents.add(n);
		}
		//String[] newData = data.replaceAll("\r", "").split("\n");
		//if (newData[0].contains("%abc")) {
		//	if (newData[0].length() > 4) {
		//		d.version = newData[0].substring(5);
		//	} else {
		//		d.version = "loose";
		//	}
		//	d.docName = newData[2].substring(2);
		//	for (int i = 1; i < newData.length; i++) {
		//		Node n = new Node();
		//		newData[i].replaceAll("\\[r:.+?\\]", "");
		//		newData[i].replaceAll("[|{}]", "");
		//		newData[i] = newData[i].substring(0, newData[i].lastIndexOf("%") == -1 ? newData[i].length() : newData[i].lastIndexOf("%"));
		//		if (newData[i].contains(":")) {
		//			int j = newData[i].lastIndexOf(":");
		//			n.fieldName = newData[i].substring(0, j);
		//			n.text = newData[i].substring(j+1);
		//			d.contents.add(n);
		//			continue;
		//		}
		//	}
		d.contents.sort(new Sorter());
		return d;
		//} else {
		//	throw new Exception("Not ABC data!");
		//}
	}
}
class Sorter implements Comparator<Node> {
	@Override
	public int compare(Node o1, Node o2) {
		// TODO Auto-generated method stub
		//System.out.println(Integer.toString(o1.sortIndex)+"\n"+Integer.toString(o2.sortIndex));
		System.out.println(Integer.toString(o1.sortIndex > o2.sortIndex ? 1 : (o1.sortIndex < o2.sortIndex ? -1 : 0)));
		return o1.sortIndex > o2.sortIndex ? 1 : (o1.sortIndex < o2.sortIndex ? -1 : 0);
	}
}
