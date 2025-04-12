package gr.aueb.cf.helloapp.controller;

import gr.aueb.cf.helloapp.model.Teacher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet("/school-app/teachers")
public class TeachersViewController extends HttpServlet {

    List<Teacher> teachers = new ArrayList<>();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        teachers = List.of(new Teacher(1L, "Μάριος","Μορφονίδης"),
                new Teacher(2L, "Νίκος","Αλειφέρης"),
                new Teacher(3L, "Ραλλού","Γκίγκου"),
                new Teacher(4L, "Δημήτρης","Παπαδόπουλος"),
                new Teacher(5L, "Κώστας","Δημητρίου"),
                new Teacher(6L, "Άρης","Νικολόπουλος"));


            String message = "";
            String filterID = request.getParameter("id");
            Long longFilterId = (filterID != null && !filterID.isEmpty()) ? Long.parseLong(filterID) : null;
            String filterFirstname = request.getParameter("firstname");
            String filterLastname = request.getParameter("lastname");

            try{

                var filteredTeachers = teachers.stream()
                        .filter(teacher ->  longFilterId == null || teacher.getId().equals(longFilterId))
                        .filter(teacher -> filterFirstname == null || filterFirstname.isEmpty() || teacher.getFirstname().startsWith(filterFirstname))
                        .filter(teacher -> filterLastname == null || filterLastname.isEmpty() || teacher.getLastname().startsWith(filterLastname))
                        .collect(Collectors.toList());

                if(filteredTeachers.isEmpty()) request.setAttribute("message", "Δεν βρέθηκαν καθηγητές με αυτά τα κριτήρια αναζήτησης");
                else request.setAttribute("teachers", filteredTeachers);

                request.getRequestDispatcher("/WEB-INF/jsp/teachers.jsp").forward(request, response);





            }catch (Exception e) {
                message = e.getMessage();
                request.setAttribute("message",message);
                request.getRequestDispatcher("/WEB-INF/jsp/teachers.jsp").forward(request, response);
            }
    }
}
