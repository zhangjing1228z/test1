import java.util.*;
import java.util.stream.*;

/**
 * 班级学生成绩查询接口模块
 *
 * 包含：
 * 1. GradeRecord       —— 成绩记录实体
 * 2. GradeQueryService —— 查询接口定义（面向外部暴露的 API）
 * 3. InMemoryGradeQueryServiceImpl —— 内存版实现（可替换为 DAO/数据库版本）
 * 4. Demo              —— 运行演示入口（主类）
 *
 * 使用方式：
 *   javac GradeQueryService.java && java GradeQueryService
 */
public class GradeQueryService {

    /* ===================== 1. 成绩记录实体 ===================== */
    public static class GradeRecord {
        private final String studentId;   // 学号
        private final String studentName; // 姓名
        private final String className;   // 班级
        private final String subject;     // 科目
        private final double score;       // 分数

        public GradeRecord(String studentId, String studentName, String className,
                           String subject, double score) {
            this.studentId = studentId;
            this.studentName = studentName;
            this.className = className;
            this.subject = subject;
            this.score = score;
        }

        public String getStudentId()   { return studentId; }
        public String getStudentName() { return studentName; }
        public String getClassName()   { return className; }
        public String getSubject()     { return subject; }
        public double getScore()       { return score; }

        @Override
        public String toString() {
            return String.format("%s %s %-4s %.1f", studentId, studentName, subject, score);
        }
    }

    /* ===================== 2. 查询接口定义 ===================== */
    public interface GradeQueryAPI {

        /** 查询指定班级的全部成绩记录 */
        List<GradeRecord> queryByClass(String className);

        /** 查询指定班级、指定科目的成绩记录（按分数降序） */
        List<GradeRecord> queryByClassAndSubject(String className, String subject);

        /** 查询指定班级中某学生的成绩记录 */
        List<GradeRecord> queryByStudent(String className, String studentId);

        /** 统计指定班级、指定科目的平均分（无数据时返回 empty） */
        OptionalDouble averageScore(String className, String subject);

        /** 查询指定班级、指定科目的前 N 名 */
        List<GradeRecord> topN(String className, String subject, int n);
    }

    /* ===================== 3. 内存版实现 ===================== */
    public static class InMemoryGradeQueryServiceImpl implements GradeQueryAPI {

        private final List<GradeRecord> records;

        public InMemoryGradeQueryServiceImpl(List<GradeRecord> records) {
            this.records = new ArrayList<>(records);
        }

        @Override
        public List<GradeRecord> queryByClass(String className) {
            Objects.requireNonNull(className, "className must not be null");
            return records.stream()
                    .filter(r -> r.getClassName().equals(className))
                    .collect(Collectors.toList());
        }

        @Override
        public List<GradeRecord> queryByClassAndSubject(String className, String subject) {
            return filterSorted(className, subject);
        }

        @Override
        public List<GradeRecord> queryByStudent(String className, String studentId) {
            Objects.requireNonNull(studentId, "studentId must not be null");
            return records.stream()
                    .filter(r -> r.getClassName().equals(className)
                              && r.getStudentId().equals(studentId))
                    .collect(Collectors.toList());
        }

        @Override
        public OptionalDouble averageScore(String className, String subject) {
            return filterRecords(className, subject).stream()
                    .mapToDouble(GradeRecord::getScore)
                    .average();
        }

        @Override
        public List<GradeRecord> topN(String className, String subject, int n) {
            if (n <= 0) throw new IllegalArgumentException("n must be positive");
            return filterSorted(className, subject).stream()
                    .limit(n)
                    .collect(Collectors.toList());
        }

        /* ---------- 私有辅助方法 ---------- */
        private List<GradeRecord> filterRecords(String className, String subject) {
            return records.stream()
                    .filter(r -> r.getClassName().equals(className)
                              && r.getSubject().equals(subject))
                    .collect(Collectors.toList());
        }

        private List<GradeRecord> filterSorted(String className, String subject) {
            return filterRecords(className, subject).stream()
                    .sorted(Comparator.comparingDouble(GradeRecord::getScore).reversed())
                    .collect(Collectors.toList());
        }
    }

    /* ===================== 4. 运行演示 ===================== */
    public static void main(String[] args) {
        List<GradeRecord> data = List.of(
                new GradeRecord("20230101", "张三", "高三1班", "数学", 92.5),
                new GradeRecord("20230101", "张三", "高三1班", "语文", 85.0),
                new GradeRecord("20230102", "李四", "高三1班", "数学", 78.0),
                new GradeRecord("20230102", "李四", "高三1班", "语文", 90.5),
                new GradeRecord("20230103", "王五", "高三1班", "数学", 66.0),
                new GradeRecord("20230201", "赵六", "高三2班", "数学", 88.0)
        );

        GradeQueryAPI service = new InMemoryGradeQueryServiceImpl(data);

        System.out.println("== 查询 [高三1班] 全部成绩 ==");
        service.queryByClass("高三1班").forEach(System.out::println);

        System.out.println("\n== 查询 [高三1班] [数学] 成绩（降序）==");
        service.queryByClassAndSubject("高三1班", "数学").forEach(System.out::println);

        System.out.println("\n== 查询 [高三1班] 学号 20230101 的成绩 ==");
        service.queryByStudent("高三1班", "20230101").forEach(System.out::println);

        System.out.println("\n== [高三1班] [数学] 平均分 ==");
        service.averageScore("高三1班", "数学")
               .ifPresentOrElse(avg -> System.out.printf("%.2f%n", avg),
                                () -> System.out.println("无数据"));

        System.out.println("\n== [高三1班] [数学] 前 2 名 ==");
        service.topN("高三1班", "数学", 2).forEach(System.out::println);
    }
}
