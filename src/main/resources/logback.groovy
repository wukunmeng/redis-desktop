/**
 * Create with IntelliJ IDEA
 * Project name : user-center
 * Package name : 
 * Author : Wukunmeng
 * User : wukm
 * Date : 16-12-13
 * Time : 上午11:36
 * ---------------------------------
 * To change this template use File | Settings | File and Code Templates.
 */

//import ch.qos.logback.core.ConsoleAppender
import ch.qos.logback.classic.encoder.PatternLayoutEncoder
//import ch.qos.logback.core.rolling.RollingFileAppender
//import ch.qos.logback.core.rolling.TimeBasedRollingPolicy

//import static ch.qos.logback.classic.Level.INFO

StringBuilder p = new StringBuilder()
//%clr(%d{yyyy-MM-dd HH:mm:ss.SSS}){faint} %clr(${LOG_LEVEL_PATTERN:-%5p}) %clr(${PID:- }){magenta} %clr(---){faint} %clr([%15.15t]){faint} %clr(%-40.40logger{39}){cyan} %clr(:){faint} %m%n${LOG_EXCEPTION_CONVERSION_WORD:-%wEx}
p.append("redis-desktop")
p.append(">%d{yyyy-MM-dd/HH:mm:ss:SSS}")
p.append(" %-5p")
if(System.getProperty("PID")){
    p.append(" pid-" + System.getProperty("PID"))
} else {
    p.append(" pid-")
}
p.append("[%X]")
p.append("[%10.10t]")
p.append("%-40.40class{39}[%-5.5line]");
p.append("%m%n");
def USER_DIR = System.getProperty("user.dir");
def product = System.getProperty("product","false");
Boolean isLog = Boolean.parseBoolean(product);
appender("CONSOLE", ConsoleAppender) {
    encoder(PatternLayoutEncoder) {
        pattern = p.toString()
    }
}

if(isLog) {
    appender("FILE", RollingFileAppender) {
        file = "${USER_DIR}/log/server.log"
        rollingPolicy(TimeBasedRollingPolicy){
            fileNamePattern = "${USER_DIR}/log/server_%d{yyyy_MM_dd_HH}.zip"
            maxHistory = 240
        }
        //append = true
        //prudent = true
        encoder(PatternLayoutEncoder) {
            pattern = p.toString()
        }
    }
}
if(isLog) {
    root(INFO, ["CONSOLE","FILE"])
} else {
    root(INFO, ["CONSOLE"])
}