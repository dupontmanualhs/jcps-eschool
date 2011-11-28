package jdohelpers;

import scala.xml.Node;
import scala.xml.NodeSeq;
import scala.xml.XML;

public class HtmlString {
	private NodeSeq value;
	
	public HtmlString(String value) {
		this.set(value);
	}
	
	public void set(String value) {
		Node node = XML.loadString("<dummy>" + value + "</dummy>");
		this.value = NodeSeq.fromSeq(node.child());
	}
	
	public NodeSeq get() {
		return this.value;
	}
	
	public void set(NodeSeq value) {
		this.value = value;
	}
}
