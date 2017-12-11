package com.example.dobrowol.styloweplywanie.utils;

import android.content.Context;
import android.support.annotation.Nullable;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

/**
 * Created by dobrowol on 05.04.17.
 */
public class XMLDataUtil implements IDataUtil {
    private Context context;
    private final String prefix = "stylowe_";

    public XMLDataUtil(Context cntxt) {
        context = cntxt;
    }

    @Override
    public void saveTeamData(TeamData teamData) {
        try {

            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

            // root elements
            Document doc = docBuilder.newDocument();
            Element rootElement = doc.createElement("Team");
            doc.appendChild(rootElement);

            Attr teamName = doc.createAttribute("name");
            teamName.setValue(teamData.teamName);
            rootElement.setAttributeNode(teamName);
            // staff elements
            Element staff = doc.createElement("Staff");
            rootElement.appendChild(staff);

            // set attribute to staff element
            Attr attr = doc.createAttribute("id");
            attr.setValue("1");
            staff.setAttributeNode(attr);

            // shorten way
            // staff.setAttribute("id", "1");

            // firstname elements
            Element firstname = doc.createElement("name");
            firstname.appendChild(doc.createTextNode(teamData.coachName));
            staff.appendChild(firstname);

            // lastname elements
            Element lastname = doc.createElement("position");
            lastname.appendChild(doc.createTextNode("coach"));
            staff.appendChild(lastname);

            Element students = doc.createElement("Students");
            for (StudentData studentData : teamData.students) {
                Element name = doc.createElement("name");
                name.appendChild(doc.createTextNode(studentData.name));
                students.appendChild(name);
                Element surname = doc.createElement("surname");
                surname.appendChild(doc.createTextNode(studentData.surname));
                students.appendChild(surname);
                Element age = doc.createElement("dateOfBirth");

                age.appendChild(doc.createTextNode(studentData.age));
                students.appendChild(age);
            }
            rootElement.appendChild(students);
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = null;

            transformer = transformerFactory.newTransformer();

            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(context.getFilesDir(), prefix + teamData.teamName + ".xml"));

            // Output to console for testing
            // StreamResult result = new StreamResult(System.out);

            transformer.transform(source, result);

        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        catch (TransformerConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }

        @Override
    public TeamData retrieveTeamData(String teamName) {
            String filename = prefix + teamName+".xml";
            File file = new File(context.getFilesDir(), filename);
            TeamData teamData = getTeamData(file);
        return teamData;
    }

    @Nullable
    private TeamData getTeamData(File fileName) {
        TeamData teamData = null;
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = null;
        Document doc = null;
        try {
            builder = factory.newDocumentBuilder();

            doc = builder.parse(fileName);

            XPathFactory xPathfactory = XPathFactory.newInstance();
            XPath xpath = xPathfactory.newXPath();

            XPathExpression expr = xpath.compile("/Team/Staff[position='coach']/name/text()");
            XPathExpression exprTeamName = xpath.compile("/Team");
            NodeList nodeCoachName = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
            NodeList nodeTeamName = (NodeList) exprTeamName.evaluate(doc, XPathConstants.NODESET);
            NamedNodeMap map = nodeTeamName.item(0).getAttributes();

            teamData= new TeamData(map.getNamedItem("name").getNodeValue(), nodeCoachName.item(0).getNodeValue());

            XPathExpression exprStudents = xpath.compile("/Team/Students");

            //Double count = (Double) exprStudents.evaluate(doc, XPathConstants.NUMBER);
            NodeList nodeStudents = (NodeList) exprStudents.evaluate(doc, XPathConstants.NODESET);

            StudentData studentData = null;

            for (int i = 0; i < nodeStudents.getLength(); i++) {
                studentData = new StudentData();
                Element el = (Element) nodeStudents.item(i);
                Node nameNode = el.getElementsByTagName("name").item(0);
                Node surnameNode = el.getElementsByTagName("surname").item(0);
                Node ageNode = el.getElementsByTagName("dateOfBirth").item(0);
                if (nameNode !=null)
                    studentData.name = el.getElementsByTagName("name").item(0).getFirstChild().getNodeValue();
                if (surnameNode != null)
                    studentData.surname = el.getElementsByTagName("surname").item(0).getFirstChild().getNodeValue();
                if (ageNode != null)
                    studentData.age = el.getElementsByTagName("dateOfBirth").item(0).getFirstChild().getNodeValue();
                teamData.addStudent(studentData);
            }

        } catch (XPathExpressionException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        return teamData;
    }

    @Override
    public ArrayList<TeamData> getTeams() {
        ArrayList<TeamData> returnedList = new ArrayList<TeamData>();
        File[] foundFiles = getFiles();

        for (File file : foundFiles) {
            TeamData teamData = getTeamData(file);
            if (teamData != null) {
                returnedList.add(teamData);
            }
        }
        return returnedList;
    }

    @Override
    public void clearCache() {

    }
    private File[] getFiles() {
        File dir = new File(context.getFilesDir(), ".");
        return dir.listFiles(new FilenameFilter() {
            public boolean accept(File dir1, String name) {
                return name.startsWith(prefix) && name.endsWith(".xml");
            }
        });
    }
}
