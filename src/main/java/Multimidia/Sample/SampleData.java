package Multimidia.Sample;

import Persistencia.modelTemp.ModelEmployee;
import Persistencia.modelTemp.ModelProfile;
import raven.swing.AvatarIcon;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class SampleData {

    public static List<ModelEmployee> getSampleEmployeeData(boolean defaultIcon) {
        List<ModelEmployee> list = new ArrayList<>();
        list.add(new ModelEmployee("20-August-2024", 1750, "Business Analyst", "Analytical thinker with experience in business process improvement.", new ModelProfile(getProfileIcon("profile_1.jpg", defaultIcon), "Hannah Scott", "Washington, D.C.")));
        list.add(new ModelEmployee("15-May-2024", 1200, "Marketing Manager", "Experienced marketing professional with a focus on digital advertising.", new ModelProfile(getProfileIcon("profile_2.jpg", defaultIcon), "Samantha Smith", "New York City")));
        list.add(new ModelEmployee("20-May-2024", 1500, "Software Engineer", "Skilled developer proficient in Java, Python, and JavaScript.", new ModelProfile(getProfileIcon("profile_3.jpg", defaultIcon), "John Johnson", "Los Angeles")));
        list.add(new ModelEmployee("25-May-2024", 1300, "Graphic Designer", "Creative designer with expertise in Adobe Creative Suite.", new ModelProfile(getProfileIcon("profile_4.jpg", defaultIcon), "Emily Brown", "Chicago")));
        list.add(new ModelEmployee("30-May-2024", 1800, "Financial Analyst", "Analytical thinker with a background in financial modeling and forecasting.", new ModelProfile(getProfileIcon("profile_5.jpg", defaultIcon), "Michael Davis", "San Francisco")));
        list.add(new ModelEmployee("15-August-2024", 1450, "Financial Planner", "Certified financial planner with a client-centered approach.", new ModelProfile(getProfileIcon("profile_6.jpg", defaultIcon), "Justin White", "San Diego")));
        list.add(new ModelEmployee("10-June-2024", 1700, "Sales Representative", "Proven track record in sales and client relationship management.", new ModelProfile(getProfileIcon("profile_7.jpg", defaultIcon), "David Martinez", "Miami")));
        list.add(new ModelEmployee("30-June-2024", 1900, "Project Manager", "Organized leader skilled in managing cross-functional teams.", new ModelProfile(getProfileIcon("profile_8.jpg", defaultIcon), "Ryan Anderson", "Portland")));
        list.add(new ModelEmployee("20-June-2024", 1550, "UX/UI Designer", "Design thinker focused on creating intuitive user experiences.", new ModelProfile(getProfileIcon("profile_9.jpg", defaultIcon), "Daniel Wilson", "Austin")));
        list.add(new ModelEmployee("20-August-2024", 1750, "Business Analyst", "Analytical thinker with experience in business process improvement.", new ModelProfile(getProfileIcon("profile_1.jpg", defaultIcon), "Hannah Scott", "Washington, D.C.")));
    list.add(new ModelEmployee("15-May-2024", 1200, "Marketing Manager", "Experienced marketing professional with a focus on digital advertising.", new ModelProfile(getProfileIcon("profile_2.jpg", defaultIcon), "Samantha Smith", "New York City")));
    list.add(new ModelEmployee("20-May-2024", 1500, "Software Engineer", "Skilled developer proficient in Java, Python, and JavaScript.", new ModelProfile(getProfileIcon("profile_3.jpg", defaultIcon), "John Johnson", "Los Angeles")));
    list.add(new ModelEmployee("25-May-2024", 1300, "Graphic Designer", "Creative designer with expertise in Adobe Creative Suite.", new ModelProfile(getProfileIcon("profile_4.jpg", defaultIcon), "Emily Brown", "Chicago")));
    list.add(new ModelEmployee("30-May-2024", 1800, "Financial Analyst", "Analytical thinker with a background in financial modeling and forecasting.", new ModelProfile(getProfileIcon("profile_5.jpg", defaultIcon), "Michael Davis", "San Francisco")));
    list.add(new ModelEmployee("15-August-2024", 1450, "Financial Planner", "Certified financial planner with a client-centered approach.", new ModelProfile(getProfileIcon("profile_6.jpg", defaultIcon), "Justin White", "San Diego")));
    list.add(new ModelEmployee("10-June-2024", 1700, "Sales Representative", "Proven track record in sales and client relationship management.", new ModelProfile(getProfileIcon("profile_7.jpg", defaultIcon), "David Martinez", "Miami")));
    list.add(new ModelEmployee("30-June-2024", 1900, "Project Manager", "Organized leader skilled in managing cross-functional teams.", new ModelProfile(getProfileIcon("profile_8.jpg", defaultIcon), "Ryan Anderson", "Portland")));
    list.add(new ModelEmployee("20-June-2024", 1550, "UX/UI Designer", "Design thinker focused on creating intuitive user experiences.", new ModelProfile(getProfileIcon("profile_9.jpg", defaultIcon), "Daniel Wilson", "Austin")));
    list.add(new ModelEmployee("10-July-2024", 1400, "Human Resources Specialist", "HR expert with a background in employee relations and recruitment.", new ModelProfile(getProfileIcon("profile_10.jpg", defaultIcon), "Laura Taylor", "Seattle")));
    list.add(new ModelEmployee("05-April-2024", 2000, "Operations Manager", "Experienced operations manager with a knack for optimizing processes.", new ModelProfile(getProfileIcon("profile_11.jpg", defaultIcon), "Kevin Hall", "Boston")));
    list.add(new ModelEmployee("18-February-2024", 1600, "Quality Assurance Engineer", "Detail-oriented QA engineer skilled in automated and manual testing.", new ModelProfile(getProfileIcon("profile_12.jpg", defaultIcon), "Sophia Lewis", "Houston")));
    list.add(new ModelEmployee("12-March-2024", 1250, "Data Scientist", "Data scientist with experience in machine learning and data analysis.", new ModelProfile(getProfileIcon("profile_13.jpg", defaultIcon), "Alex Turner", "Phoenix")));
    list.add(new ModelEmployee("28-January-2024", 1350, "Supply Chain Specialist", "Expert in supply chain logistics and inventory management.", new ModelProfile(getProfileIcon("profile_14.jpg", defaultIcon), "Carol Walker", "Dallas")));
    list.add(new ModelEmployee("03-March-2024", 1450, "Network Engineer", "Network engineer skilled in designing and troubleshooting networks.", new ModelProfile(getProfileIcon("profile_15.jpg", defaultIcon), "Oscar Gonzalez", "San Antonio")));
    list.add(new ModelEmployee("25-December-2023", 1750, "Cybersecurity Specialist", "Certified cybersecurity specialist with experience in threat analysis.", new ModelProfile(getProfileIcon("profile_16.jpg", defaultIcon), "Paul Adams", "Orlando")));
    list.add(new ModelEmployee("14-November-2023", 1150, "Legal Advisor", "Legal advisor with expertise in corporate and labor law.", new ModelProfile(getProfileIcon("profile_17.jpg", defaultIcon), "Karen Clark", "Denver")));
    list.add(new ModelEmployee("06-September-2023", 1600, "Customer Success Manager", "Passionate about helping clients succeed and improve engagement.", new ModelProfile(getProfileIcon("profile_18.jpg", defaultIcon), "Megan Hernandez", "Atlanta")));
    list.add(new ModelEmployee("22-October-2023", 1550, "Product Designer", "Creative product designer with a focus on usability and aesthetics.", new ModelProfile(getProfileIcon("profile_19.jpg", defaultIcon), "Tom Parker", "Philadelphia")));
    list.add(new ModelEmployee("11-September-2023", 1700, "Content Strategist", "Content expert with experience in SEO and content marketing.", new ModelProfile(getProfileIcon("profile_20.jpg", defaultIcon), "Grace Young", "Las Vegas")));
        return list;
    }

    public static List<ModelEmployee> getSampleBasicEmployeeData() {
        List<ModelEmployee> list = new ArrayList<>();
        list.add(new ModelEmployee("20-August-2024", 1750, "Business Analyst", "Analytical thinker with experience in business process improvement.", new ModelProfile(null, "Hannah Scott", "Washington, D.C.")));
        list.add(new ModelEmployee("15-May-2024", 1200, "Marketing Manager", "Experienced marketing professional with a focus on digital advertising.", new ModelProfile(null, "Samantha Smith", "New York City")));
        list.add(new ModelEmployee("20-May-2024", 1500, "Software Engineer", "Skilled developer proficient in Java, Python, and JavaScript.", new ModelProfile(null, "John Johnson", "Los Angeles")));
        list.add(new ModelEmployee("25-May-2024", 1300, "Graphic Designer", "Creative designer with expertise in Adobe Creative Suite.", new ModelProfile(null, "Emily Brown", "Chicago")));
        list.add(new ModelEmployee("30-May-2024", 1800, "Financial Analyst", "Analytical thinker with a background in financial modeling and forecasting.", new ModelProfile(null, "Michael Davis", "San Francisco")));
        list.add(new ModelEmployee("5-June-2024", 1600, "HR Manager", "Human resources professional specializing in recruitment and employee relations.", new ModelProfile(null, "Jessica Miller", "Seattle")));
        list.add(new ModelEmployee("10-June-2024", 1700, "Sales Representative", "Proven track record in sales and client relationship management.", new ModelProfile(null, "David Martinez", "Miami")));
        list.add(new ModelEmployee("15-June-2024", 1400, "Content Writer", "Versatile writer capable of producing engaging content across various platforms.", new ModelProfile(null, "Sarah Thompson", "Boston")));
        list.add(new ModelEmployee("20-June-2024", 1550, "UX/UI Designer", "Design thinker focused on creating intuitive user experiences.", new ModelProfile(null, "Daniel Wilson", "Austin")));
        list.add(new ModelEmployee("25-June-2024", 1350, "Accountant", "Detail-oriented accountant with expertise in financial reporting.", new ModelProfile(null, "Rachel Taylor", "Denver")));
        list.add(new ModelEmployee("30-June-2024", 1900, "Project Manager", "Organized leader skilled in managing cross-functional teams.", new ModelProfile(null, "Ryan Anderson", "Portland")));
        list.add(new ModelEmployee("5-July-2024", 1750, "Marketing Coordinator", "Marketing professional with experience in campaign management and analysis.", new ModelProfile(null, "Lauren Hernandez", "Phoenix")));
        list.add(new ModelEmployee("10-July-2024", 1650, "Software Developer", "Full-stack developer proficient in front-end and back-end technologies.", new ModelProfile(null, "Kevin Garcia", "Atlanta")));
        list.add(new ModelEmployee("15-July-2024", 1300, "Customer Service Representative", "Dedicated customer service professional committed to resolving issues.", new ModelProfile(null, "Amanda Martinez", "Houston")));
        list.add(new ModelEmployee("20-July-2024", 1600, "Data Analyst", "Analytical thinker with expertise in data visualization and statistical analysis.", new ModelProfile(null, "Erica Robinson", "Philadelphia")));
        list.add(new ModelEmployee("25-July-2024", 1850, "Operations Manager", "Efficient manager with experience in optimizing operational processes.", new ModelProfile(null, "Matthew Walker", "Dallas")));
        list.add(new ModelEmployee("30-July-2024", 1400, "Social Media Manager", "Strategic thinker with a passion for creating engaging social media content.", new ModelProfile(null, "Olivia Lewis", "Detroit")));
        list.add(new ModelEmployee("5-August-2024", 1700, "Web Developer", "Skilled web developer with expertise in HTML, CSS, and JavaScript frameworks.", new ModelProfile(null, "Nathan King", "Minneapolis")));
        list.add(new ModelEmployee("10-August-2024", 1550, "Digital Marketing Specialist", "Experienced marketer focused on digital advertising and SEO strategies.", new ModelProfile(null, "Maria Perez", "Orlando")));
        list.add(new ModelEmployee("15-August-2024", 1450, "Financial Planner", "Certified financial planner with a client-centered approach.", new ModelProfile(null, "Justin White", "San Diego")));
        return list;
    }

    private static Icon getProfileIcon(String name, boolean defaultIcon) {
        if (defaultIcon) {
            return new ImageIcon(SampleData.class.getResource("/raven/modal/demo/images/" + name));
        } else {
            AvatarIcon avatarIcon = new AvatarIcon(SampleData.class.getResource("/Multimidia/imagens/profile.png" + name), 55, 55, (int) 3f);
            return avatarIcon;
        }
    }
}
