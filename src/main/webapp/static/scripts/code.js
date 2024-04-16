// Переменные ответственные за элементы на интерфейсе
const slider = document.getElementById("size-slider");
const toolBtns = document.querySelectorAll(".tool");
var canvas = null;
const heatingCheckBox = document.getElementById("heating");
const electricityCheckBox = document.getElementById("electricity");
const labelInput = document.getElementById("label");
const valueInput = document.getElementById("value");
const checkbox = document.querySelectorAll("input[type=checkbox]");
const saveBtn = document.getElementById("saveBtn");
const inputElement = document.getElementById("file");
const exportBtn = document.getElementById("exportBtn");
const createBtn = document.getElementById("createBtn");
const checkbox1 = document.getElementById("checkbox1");
const checkbox2 = document.getElementById("checkbox2");
const openBtn = document.getElementById("openBtn");

// Переменные, необходимые для визуализации
const container = document.getElementById("cy");
const layout = { name: "preset" };
var cy = null;

// Переменные с начальным значением
var nodeType,
  systemType = null;
var nodeId = 10;
var edgeId = 10;
var clickCount = 0;
var toolIsSelected = false;
var firstNodeSelected,
  secondNodeSelected,
  selectedNode,
  selectedEdge = null;
var addEdgeOn,
  addNodeOn,
  editModeOn,
  delModeOn = false;

var dropdown = document.getElementById("test");

// Функция для добавления элементов в выпадающий список
function populateDropdown(data) {
  for (var i = 0; i < data.length; i++) {
    var option = document.createElement("option");
    option.text = data[i].name;
    option.value = data[i].id;
    dropdown.add(option);
  }
}

fetch("http://localhost:8080/app/api/get/lines")
  .then((res) => res.json())
  .then((data) => {
    populateDropdown(data);
  });

// Обработка события при открытии файла
inputElement.addEventListener("change", function (e) {
  var file = e.target.files[0];
  var url = URL.createObjectURL(file);
  setModeValue(false, false, false, false);
  start(new Request(url));
});

openBtn.addEventListener("click", () => {
  console.log("open");
   let url = "http://localhost:8080/app/api/get/model?name=graph2";
   setModeValue(false, false, false, false);
   start(new Request(url));
});

// Подумать, как можно переделать чтобы только один раз грузить стиль, а остальные разы перезагружать данные графа
function start(request) {
  Promise.all([
    // fetch("cy-style.json").then(function (res) {
    //   return res.json();
    // }),
    fetch(request).then((res) => {
      return res.json();
    }),
  ]).then((dataArray) => {
    // console.log(dataArray[0].style);
    // Создаём экземпляр графа
    cy = window.cy = cytoscape({
      container: container,
      style: dataArray[0].style,
      elements: dataArray[0].elements,
      layout: layout,
    });

    // Задаём параметры приближения и положения
    cy.zoom(1.25);
    cy.pan({ x: 270, y: 450 });

    // Получаем все элементы графа
    // var elements = cy.elements();
    // console.log(elements.json());
    // // Перебираем все элементы и выводим их данные
    // elements.forEach(function (element) {
    //   console.log(element.data());
    // });
    // console.log(createVect());
    // console.log(createMatrix());
    canvas = document.querySelector("canvas");

    // Обработка нажатий на элементы графа
    cy.on("click", "node", function (event) {
      var node = event.target;
      var id = node.data("id");
      var equipment = node.data("equipment");
      selectedNode = node;
      systemType = node.data("systemtype");
      // console.log(systemType);
      addEdge(id);

      if (editModeOn) {
        if (systemType === "теплоснабжение") {
          checkbox1.checked = true;
        } else if (systemType === "электроснабжение") {
          checkbox2.checked = true;
        }
        //прверка на тип элемента источник и прочее
        equipment.forEach((element) => {
          labelInput.value = element.name;
          valueInput.value = element.max_gen;
          // console.log(element.label);
          // console.log(element.value);
        });
      }
      // editModeOn = false; проблемы, когда кликаешь, то кнопка сохранить не работает
    });

    cy.on("click", "edge", function (event) {
      var edge = event.target;
      var id = edge.data("id");
      var equipment = edge.data("equipment");
      selectedEdge = edge;
      // console.log(equipment);

      if (editModeOn) {
        if (systemType === "теплоснабжение") {
          checkbox1.checked = true;
        } else if (systemType === "электроснабжение") {
          checkbox2.checked = true;
        }
        equipment.forEach((element) => {
          // console.log(element.label);
          // console.log(element.value);
        });
      }
      // editModeOn = false;
    });

    // Обработка нажатий на кнопки
    toolBtns.forEach((btn) => {
      btn.addEventListener("click", () => {
        setActiveToBtn(document.querySelector(".active"), btn);
        btnSelected(btn.id);
        toolIsSelected = true;
        // console.log(btn.id);
      });
    });

    saveBtn.addEventListener("click", () => {
      // .catch((e) => console.log(e));
      console.log(selectedNode.data("systemtype"));
      // console.log(labelInput.value);
      // console.log(valueInput.value);
      // console.log(editModeOn);
      if (checkbox1.checked) {
        systemType = "теплоснабжение";
      } else if (checkbox2.checked) {
        systemType = "электроснабжение";
      }
      // console.log(systemType);
      if (editModeOn) {
        selectedNode.data("systemtype", systemType);
        selectedNode.data("equipment", [
          {
            name: labelInput.value,
            price: 0.0,
            throughput: 0,
            resistance: 0,
            cost: 0,
            max_gen: `${valueInput.value}`,
            min_gen: 0,
            load: 3,
          },
        ]);
      }
      checkbox1.checked = false;
      checkbox2.checked = false;
      // console.log(selectedNode.data("systemtype"));
      editModeOn = false;
    });

    exportBtn.addEventListener("click", () => {
      var graphJSON = cy.json();

      // var graphJSONString = JSON.stringify(graphJSON);
      // // console.log(graphJSONString);
      // var blob = new Blob([graphJSONString], { type: "application/json" });
      // var url = URL.createObjectURL(blob);

      // var link = document.createElement("a");
      // link.href = url;
      // link.download = "graph data.json";
      // link.click();

      let xhr = new XMLHttpRequest();
      let url = "http://localhost:8080/app/api/post/saveModel";

      xhr.open("POST", url, true);
      xhr.setRequestHeader("Content-Type", "application/json");

      let data = JSON.stringify(graphJSON);
      xhr.send(data);

      xhr.onreadystatechange = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
          // Выводим ответ сервера
          console.log(xhr.responseText);
        }
      };
    });

    createBtn.addEventListener("click", () => {
      sendJSON();
    });

    // Обработка нажатия на поле рисования
    canvas.addEventListener("click", (e) => {
      //подставить значения из полей
      addNode(e);
      toolIsSelected = false;
    });
  });
}

// Вспомогательные функции
function setModeValue(v1, v2, v3, v4) {
  addNodeOn = v1;
  addEdgeOn = v2;
  editModeOn = v3;
  delModeOn = v4;
}

function setActiveToBtn(activeOption, btn) {
  if (activeOption != null) {
    activeOption.classList.remove("active");
  }
  btn.classList.add("active");
}

function btnSelected(btnId) {
  switch (btnId) {
    case "triangle":
      nodeType = "источник";
      setModeValue(true, false, false, false);
      break;
    case "rectangle":
      nodeType = "соединение";
      setModeValue(true, false, false, false);
      break;
    case "circle":
      nodeType = "потребитель";
      setModeValue(true, false, false, false);
      break;
    case "edit":
      setModeValue(false, false, true, false);
      break;
    case "add":
      setModeValue(false, true, false, false);
      break;
    case "delEdge":
      selectedEdge.remove();
      setModeValue(false, true, false, true);
      break;
    case "del":
      selectedNode.remove();
      setModeValue(false, true, false, true);
      break;
    default:
      nodeType = null;
      setModeValue(false, false, false, false);
  }
}

function addEdge(id) {
  if (addEdgeOn) {
    if (clickCount == 0) {
      firstNodeSelected = id;
      clickCount++;
    } else if (clickCount == 1) {
      secondNodeSelected = id;
      var edge = [
        {
          data: {
            id: `e${firstNodeSelected}${secondNodeSelected}`,
            source: firstNodeSelected,
            target: secondNodeSelected,
            systemtype: "теплоснабжение",
            length: 20,
            equipment: [
              {
                name: "ребро",
                price: 0.0,
                throughput: 0,
                resistance: 1,
                cost: 10,
                max_gen: 0,
                min_gen: 0,
                load: 0,
              },
            ],
          },
        },
      ];
      cy.add(edge);
      clickCount = 0;
      firstNodeSelected = null;
      secondNodeSelected = null;
      addEdgeOn = false;
    }
  }
}

function addNode(e) {
  if (toolIsSelected && addNodeOn) {
    var node = [
      {
        data: {
          id: nodeId++,
          nodetype: nodeType,
          systemtype: "теплоснабжение",
          length: 0,
          equipment: [
            {
              name: `узел ${nodeId}`,
              price: 0.0,
              throughput: 0,
              resistance: 0,
              cost: 0,
              max_gen: 0,
              min_gen: 0,
              load: 0,
            },
          ],
        },
        position: { x: e.offsetX - 270, y: e.offsetY - 450 },
      },
    ];
    cy.add(node);
    addNodeOn = false;
  }
}

function sendJSON() {
  var graphJSON = cy.json();
  let xhr = new XMLHttpRequest();
  let url = "http://localhost:8080/app/api/post/calculate";

  xhr.open("POST", url, true);
  xhr.setRequestHeader("Content-Type", "application/json");

  let data = JSON.stringify(graphJSON);
  xhr.send(data);

  xhr.onreadystatechange = function () {
    if (xhr.readyState === 4 && xhr.status === 200) {
      // Выводим ответ сервера
      console.log(xhr.responseText);
    }
  };
}
