package tuw_msgs_java_interface;

import java.time.LocalTime;

import org.ros.namespace.GraphName;
import org.ros.node.AbstractNodeMain;
import org.ros.node.ConnectedNode;
import org.ros.node.topic.Subscriber;

import geometry_msgs.Point;
import tuw_multi_robot_msgs.RobotInfo;

public class Example extends AbstractNodeMain {
    @Override
    public GraphName getDefaultNodeName() {
        return GraphName.of("ait/example");
    }

    @Override
    public void onStart(ConnectedNode connectedNode) {
        Subscriber<RobotInfo> s = connectedNode.newSubscriber("/robot_info", RobotInfo._TYPE);
        s.addMessageListener(robotInfo -> {
            Point p = robotInfo.getPose().getPose().getPosition();
	    System.out.println("changed");
            System.out.printf("seq=%1$-5d %2$tT.%2$tL: %3$s@(%4$.3f,%5$.3f)%n",
                              robotInfo.getHeader().getSeq(),
                              LocalTime.now(),
                              robotInfo.getRobotName(),
                              p.getX(),
                              p.getY());
        }, 1000);
    }
}
