package com.example.baitap123

import android.app.Activity
import android.os.Bundle
import android.widget.TextView

data class Student(val name: String, val email: String?, val phone: String?)
data class ScoreStudent(val name: String, val score: Double?)

class MainActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        showStudents()
        showGrades()
        showClassScores()
    }

    // BAI 1: for, nullable va toan tu ?:.
    private fun showStudents() {
        val students = listOf(
            Student("Nguyễn Văn An", "an@sv.edu.vn", "0901 234 567"),
            Student("Trần Thị Bình", null, "0912 888 999"),
            Student("Lê Minh Chi", "chi@sv.edu.vn", null),
            Student("Phạm Quốc Dũng", null, null),
            Student("Võ Thanh Em", "em@sv.edu.vn", "0933 111 222")
        )
        var text = ""
        var missingEmail = 0
        for (student in students) {
            val email = student.email ?: "Chưa cập nhật"
            val phone = student.phone ?: "Chưa cập nhật"
            if (student.email == null) missingEmail++
            text += "${student.name}\nEmail: $email\nSĐT: $phone\n\n"
        }
        text += "Tổng: ${students.size} sinh viên\nThiếu email: $missingEmail"
        findViewById<TextView>(R.id.studentList).text = text
    }

    // BAI 2: if/else xep loai diem.
    private fun grade(score: Double): String {
        if (score < 0 || score > 10) return "Không hợp lệ"
        else if (score >= 8) return "Giỏi"
        else if (score >= 6.5) return "Khá"
        else if (score >= 5) return "Trung bình"
        else return "Yếu"
    }

    private fun showGrades() {
        val scores = listOf(9.0, 7.0, 5.5, 3.0, 11.0)
        var text = "Điểm ban đầu: 8.5 - ${grade(8.5)}\n\nKiểm tra thêm:\n"
        for (score in scores) {
            text += "Điểm $score: ${grade(score)}\n"
        }
        findViewById<TextView>(R.id.gradeList).text = text
    }

    // BAI 3: for + if + nullable, thong ke bang diem.
    private fun showClassScores() {
        val students = listOf(
            ScoreStudent("Nguyễn Văn An", 9.0),
            ScoreStudent("Trần Thị Bình", 7.2),
            ScoreStudent("Lê Minh Chi", null),
            ScoreStudent("Phạm Quốc Dũng", 4.5),
            ScoreStudent("Võ Thanh Em", 5.8)
        )
        var total = 0.0
        var countHasScore = 0
        var goodCount = 0
        var noScoreCount = 0
        var highest = 0.0
        var text = ""

        for (student in students) {
            if (student.score == null) {
                text += "${student.name}: Chưa có điểm\n"
                noScoreCount++
            } else {
                val score = student.score
                total += score
                countHasScore++
                if (grade(score) == "Giỏi") goodCount++
                if (score > highest) highest = score
                text += "${student.name}: $score - ${grade(score)}\n"
            }
        }

        val average = if (countHasScore > 0) total / countHasScore else 0.0
        val summary = "Điểm trung bình lớp: ${"%.2f".format(average)}\n" +
            "Số sinh viên Giỏi: $goodCount\n" +
            "Chưa có điểm: $noScoreCount\n" +
            "Điểm cao nhất: $highest\n\n"
        findViewById<TextView>(R.id.classResult).text = summary + text
    }
}
