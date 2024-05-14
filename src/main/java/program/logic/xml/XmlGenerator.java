package program.logic.xml;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.io.File;



public class XmlGenerator {

    public void generate() {
        try {
            // Создание документа
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.newDocument();

            // Создание корневого элемента
            Element rootElement = document.createElement("document");
            document.appendChild(rootElement);

            // Добавление категории
            Element categoryElement = document.createElement("category");
            categoryElement.setTextContent("go");
            rootElement.appendChild(categoryElement);

            // Добавление других элементов...
            // Пример добавления элемента email
            Element emailElement = document.createElement("email");
            emailElement.setTextContent("cr4nkle@yandex.ru");
            rootElement.appendChild(emailElement);

            // Добавление элемента client
            Element clientElement = document.createElement("client");
            clientElement.setTextContent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/122.0.0.0 YaBrowser/24.4.0.0 Safari/537.36@176.59.146.25");
            rootElement.appendChild(clientElement);

            // Добавление элемента priority
            Element priorityElement = document.createElement("priority");
            priorityElement.setTextContent("long");
            rootElement.appendChild(priorityElement);

            // Добавление элемента model
            Element modelElement = document.createElement("model");
            modelElement.setTextContent("ВАШИ МОДЕЛЬНЫЕ ДАННЫЕ ЗДЕСЬ");
            rootElement.appendChild(modelElement);

            // Добавление элемента data
            Element dataElement = document.createElement("data");
            dataElement.setTextContent("ВАШИ ДАННЫЕ ЗДЕСЬ");
            rootElement.appendChild(dataElement);

            // Добавление элемента commands
            Element commandsElement = document.createElement("commands");
            commandsElement.setTextContent("solve;\ndisplay x;");
            rootElement.appendChild(commandsElement);

            // Добавление элемента comments
            Element commentsElement = document.createElement("comments");
            commentsElement.setTextContent("");
            rootElement.appendChild(commentsElement);

            // Сохранение документа в файл
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(new File("output.xml"));

            transformer.transform(source, result);

            System.out.println("XML документ успешно сохранен.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

