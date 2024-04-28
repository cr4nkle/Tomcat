const create = document.getElementById("new");
const uploadFromPc = document.getElementById("upload-from-pc");
const uploadFromDb = document.getElementById("upload-from-db");
const saveToDb = document.getElementById("save-to-db");
const download = document.getElementById("download");
const deleteModel = document.getElementById("delete-model");
const deleteElement = document.getElementById("delete-element");
const calculate = document.getElementById("calculate");
const move = document.getElementById("move");
const toolBtns = document.querySelectorAll(".tool");
const hideCheckboxes = document.querySelectorAll(".group1");
const editCheckboxes = document.querySelectorAll(".group2");
//эндпоинты
const styleUrl = "http://localhost:8080/app/api/get/style";
const getLinesUrl = "http://localhost:8080/app/api/get/lines";
const getConsumerUrl = "http://localhost:8080/app/api/get/consumers";
const getSourceUrl = "http://localhost:8080/app/api/get/sources";
const saveModelUrl = "http://localhost:8080/app/api/post/saveModel";
const calculateUrl = "http://localhost:8080/app/api/post/calculate";
//переменные с начальным значением
var addEdgeOn,
  addNodeOn,
  editModeOn,
  delModeOn = false;
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
var modelName;
// Переменные, необходимые для визуализации
const container = document.getElementById("cy");
const layout = { name: "preset" };
var cy = null;
var canvas = null;

create.addEventListener("click", async () => {
  modelName = prompt("Введите название модели:", "Модель");
  if (modelName) {
    alert("Вы ввели: " + modelName);
  } else {
    alert("Ввод отменен");
  }
  setModeValue(false, false, false, false);
  cy = await initializeEmptyGraph(container);
  initializeGraphMethods(cy);
});

uploadFromPc.addEventListener("change", async function (e) {
  var file = e.target.files[0];
  modelName = file.name;
  var url = URL.createObjectURL(file);
  setModeValue(false, false, false, false);
  cy = await initializeGraph(new Request(url), container);
  initializeGraphMethods(cy);
});

uploadFromDb.addEventListener("click", async () => {
  modelName = prompt("Введите название модели:", "Модель");
  if (modelName) {
    alert("Вы ввели: " + modelName);
  } else {
    alert("Ввод отменен");
  }
  let url = `http://localhost:8080/app/api/get/model?name=${modelName}`;
  cy = await initializeGraph(new Request(url), container);
  initializeGraphMethods(cy);
  setModeValue(false, false, false, false);
});

saveToDb.addEventListener("click", async () => {
  if (cy !== null) {
    var graphJSON = cy.json();
    graphJSON.name = removeExtension(modelName);
    let data = JSON.stringify(graphJSON);
    let result = await loadDataOnServer(saveModelUrl, data, "POST");
  }
});

download.addEventListener("click", () => {
  downloadGraph(modelName);
});

deleteModel.addEventListener("click", () => {
  let deleteUrl = `http://localhost:8080/app/api/delete/model?name=${modelName}`;
  deleteDataFromServer(deleteUrl);
});

toolBtns.forEach((btn) => {
  btn.addEventListener("click", () => {
    setActiveToBtn(document.querySelector(".active"), btn);
    btnSelected(btn.id);
    toolIsSelected = true;
    console.log(btn.id);
  });
});

calculate.addEventListener("click", async () => {
  if (cy !== null) {
    var graphJSON = {
      "node-id": [
        "n1",
        "n1c",
        "n1cc",
        "n2",
        "n3",
        "n4",
        "n5",
        "n6",
        "n7",
        "n8",
        "n9",
        "n10",
        "lim",
      ],
      "edge-id": [
        "e12",
        "e12c",
        "e12cc",
        "e23",
        "e24",
        "e25",
        "e26",
        "e27",
        "e28",
        "e89",
        "e810",
        "e12d",
        "e12cd",
        "e12ccd",
        "sign",
        "lim",
      ],
      node: {
        n1c: {
          "node-type": "source",
          price: 3.0,
          cost: 30,
          "max-generation": 6,
          "min-generation": 0,
          load: 0,
          "system-type": "heat",
          installed: true,
        },
        n1cc: {
          "node-type": "source",
          price: 5.0,
          cost: 40,
          "max-generation": 10,
          "min-generation": 0,
          load: 0,
          "system-type": "heat",
          installed: true,
        },
        n1: {
          "node-type": "source",
          price: 2.0,
          cost: 20,
          "max-generation": 5,
          "min-generation": 0,
          load: 0,
          "system-type": "heat",
          installed: true,
        },
        n2: {
          "node-type": "connector",
          price: 0.0,
          cost: 0,
          "max-generation": 0,
          "min-generation": 0,
          load: 0,
          "system-type": "heat",
          installed: false,
        },
        n3: {
          "node-type": "consumer",
          price: 0.0,
          cost: 0,
          "max-generation": 0,
          "min-generation": 0,
          load: 2,
          "system-type": "heat",
          installed: false,
        },
        n4: {
          "node-type": "consumer",
          price: 0.0,
          cost: 0,
          "max-generation": 0,
          "min-generation": 0,
          load: 3,
          "system-type": "heat",
          installed: false,
        },
        n5: {
          "node-type": "consumer",
          price: 0.0,
          cost: 0,
          "max-generation": 0,
          "min-generation": 0,
          load: 3,
          "system-type": "heat",
          installed: false,
        },
        n6: {
          "node-type": "consumer",
          price: 0.0,
          cost: 0,
          "max-generation": 0,
          "min-generation": 0,
          load: 3,
          "system-type": "heat",
          installed: false,
        },
        n7: {
          "node-type": "consumer",
          price: 0.0,
          cost: 0,
          "max-generation": 0,
          "min-generation": 0,
          load: 3,
          "system-type": "heat",
          installed: false,
        },
        n8: {
          "node-type": "connector",
          price: 0.0,
          cost: 0,
          "max-generation": 0,
          "min-generation": 0,
          load: 0,
          "system-type": "heat",
          installed: false,
        },
        n9: {
          "node-type": "consumer",
          price: 0.0,
          cost: 0,
          "max-generation": 0,
          "min-generation": 0,
          load: 2,
          "system-type": "heat",
          installed: false,
        },
        n10: {
          "node-type": "consumer",
          price: 0.0,
          cost: 0,
          "max-generation": 0,
          "min-generation": 0,
          load: 2,
          "system-type": "heat",
          installed: false,
        },
      },
      edge: {
        e12cc: {
          "system-type": "heat",
          throughput: 5,
          resistance: 0,
          cost: 0,
          length: 10.0,
          source: "n1cc",
          target: "n2",
        },
        e12cd: {
          "system-type": "heat",
          throughput: 5,
          resistance: 0,
          cost: 0,
          length: 10.0,
          source: "n1c",
          target: "n2",
        },
        e24: {
          "system-type": "heat",
          throughput: 4,
          resistance: 0,
          cost: 0,
          length: 17.0,
          source: "n2",
          target: "n4",
        },
        e12: {
          "system-type": "heat",
          throughput: 5,
          resistance: 0,
          cost: 0,
          length: 10.0,
          source: "n1",
          target: "n2",
        },
        e23: {
          "system-type": "heat",
          throughput: 3,
          resistance: 0,
          cost: 0,
          length: 14.0,
          source: "n2",
          target: "n3",
        },
        e89: {
          "system-type": "heat",
          throughput: 4,
          resistance: 0,
          cost: 0,
          length: 17.0,
          source: "n8",
          target: "n9",
        },
        e26: {
          "system-type": "heat",
          throughput: 4,
          resistance: 0,
          cost: 0,
          length: 17.0,
          source: "n2",
          target: "n6",
        },
        e25: {
          "system-type": "heat",
          throughput: 4,
          resistance: 0,
          cost: 0,
          length: 17.0,
          source: "n2",
          target: "n5",
        },
        e28: {
          "system-type": "heat",
          throughput: 4,
          resistance: 0,
          cost: 0,
          length: 17.0,
          source: "n2",
          target: "n8",
        },
        e27: {
          "system-type": "heat",
          throughput: 4,
          resistance: 0,
          cost: 0,
          length: 17.0,
          source: "n2",
          target: "n7",
        },
        e12d: {
          "system-type": "heat",
          throughput: 5,
          resistance: 0,
          cost: 0,
          length: 10.0,
          source: "n1",
          target: "n2",
        },
        e12c: {
          "system-type": "heat",
          throughput: 5,
          resistance: 0,
          cost: 0,
          length: 10.0,
          source: "n1c",
          target: "n2",
        },
        e12ccd: {
          "system-type": "heat",
          throughput: 5,
          resistance: 0,
          cost: 0,
          length: 10.0,
          source: "n1cc",
          target: "n2",
        },
        e810: {
          "system-type": "heat",
          throughput: 4,
          resistance: 0,
          cost: 0,
          length: 17.0,
          source: "n8",
          target: "n10",
        },
      },
    };
    let data = JSON.stringify(graphJSON);
    let result = await loadDataOnServer(calculateUrl, data, "POST");
    console.log(result);
  }
});

move.addEventListener("click", () => {
  if (cy !== null) {
    displaySelectedValues();
    setChecked("", editCheckboxes);
    setModeValue(false, false, false, false);
  }
});
//делаем невидимыми
hideCheckboxes.forEach((checkbox) => {
  checkbox.addEventListener("change", function () {
    if (cy !== null) {
      if (this.checked) {
        if (this.id === "checkbox1") {
          cy.nodes('node[systemtype = "heat"]').hide();
          cy.edges('edge[systemtype = "heat"]').hide();
        } else if (this.id === "checkbox2") {
          cy.nodes('node[systemtype = "power"]').hide();
          cy.edges('edge[systemtype = "power"]').hide();
        } else if (this.id === "checkbox3") {
          cy.nodes('node[systemtype = "fuel"]').hide();
          cy.edges('edge[systemtype = "fuel"]').hide();
        }
      } else {
        cy.elements(":hidden").show();
      }
    }
  });
});
//функции для работы приложения
function removeExtension(filename) {
  return filename.substring(0, filename.lastIndexOf(".")) || filename;
}
function setModeValue(isAddNodeOn, isAddEdgeOn, isEditModeOn, isDelModeOn) {
  addNodeOn = isAddNodeOn;
  addEdgeOn = isAddEdgeOn;
  editModeOn = isEditModeOn;
  delModeOn = isDelModeOn;
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
      nodeType = "source";
      break;
    case "rectangle":
      nodeType = "connector";
      break;
    case "circle":
      nodeType = "consumer";
      break;
    case "edit":
      setModeValue(false, false, true, false);
      break;
    case "add-node":
      setModeValue(true, false, false, false);
      break;
    case "add-edge":
      setModeValue(false, true, false, false);
      break;
    case "delete-element":
      setModeValue(false, false, false, true);
      break;
    default:
      nodeType = "circle";
      setModeValue(false, false, false, false);
  }
}
function loadDataFromServer(url) {
  return new Promise((resolve, reject) => {
    let xhr = new XMLHttpRequest();

    xhr.open("GET", url, true);
    xhr.setRequestHeader("Content-Type", "application/json");
    xhr.onreadystatechange = function () {
      if (xhr.readyState === 4) {
        if (xhr.status === 200) {
          let responseArray = JSON.parse(xhr.responseText);
          resolve(responseArray);
        } else {
          reject(new Error("Ошибка запроса: " + xhr.status));
        }
      }
    };
    xhr.send();
  });
}
function loadDataOnServer(url, data, method) {
  return new Promise((resolve, reject) => {
    let xhr = new XMLHttpRequest();

    xhr.open(method, url, true);
    xhr.setRequestHeader("Content-Type", "application/json");
    xhr.onreadystatechange = function () {
      if (xhr.readyState === 4) {
        if (xhr.status === 200) {
          let responseArray = JSON.parse(xhr.responseText);
          resolve(responseArray);
        } else {
          reject(new Error("Ошибка запроса: " + xhr.status));
        }
      }
    };
    xhr.send(data);
  });
}
function deleteDataFromServer(url) {
  let xhr = new XMLHttpRequest();
  xhr.open("DELETE", url, true);
  xhr.setRequestHeader("Content-Type", "application/json");
  xhr.onreadystatechange = function () {
    if (xhr.readyState === 4) {
      if (xhr.status === 200) {
        let responseArray = JSON.parse(xhr.responseText);
        console.log(responseArray);
      } else {
        console.log(new Error("Ошибка запроса: " + xhr.status));
      }
    }
  };
  xhr.send();
}
function downloadGraph(fileName) {
  if (cy !== null) {
    var graphJSON = cy.json();
    var graphJSONString = JSON.stringify(graphJSON);
    var blob = new Blob([graphJSONString], { type: "application/json" });
    var url = URL.createObjectURL(blob);

    var link = document.createElement("a");
    link.href = url;
    link.download = fileName;
    link.click();
  } else {
    alert("Модель не создана");
  }
}
//функции для графа
function initializeGraph(request, container) {
  return new Promise((resolve, reject) => {
    Promise.all([
      fetch(request).then((res) => {
        return res.json();
      }),
      loadDataFromServer(styleUrl).then((res) => {
        return res;
      }),
    ])
      .then((dataArray) => {
        console.log(dataArray);
        const cy = cytoscape({
          container: container,
          style: dataArray[1].style,
          elements: dataArray[0].elements,
          layout: layout,
        });
        resolve(cy);
      })
      .catch((error) => {
        reject(error);
      });
  });
}
function initializeEmptyGraph(container) {
  return new Promise((resolve, reject) => {
    Promise.all([
      loadDataFromServer(styleUrl).then((res) => {
        return res;
      }),
    ])
      .then((dataArray) => {
        const cy = cytoscape({
          container: container,
          style: dataArray[0].style,
          elements: [],
          layout: layout,
        });
        resolve(cy);
      })
      .catch((error) => {
        reject(error);
      });
  });
}
function initializeGraphMethods(cy) {
  cy.zoom(1.25);
  cy.pan({ x: 270, y: 450 });
  canvas = document.querySelector("canvas");

  // Обработка нажатий на элементы графа
  cy.on("click", "node", function (event) {
    console.log("node click");
    var node = event.target;
    var id = node.data("id");
    var equipment = node.data("equipment");
    systemType = node.data("systemtype");
    addEdgeToGraph(id);

    if (editModeOn) {
      setChecked(systemType, editCheckboxes);

      equipment.forEach((element) => {
        setChecked(`${element.id}`, checkedCheckboxes);
        console.log(element.id);
      });
    } else if (delModeOn) {
      node.remove();
    }
  });

  cy.on("click", "edge", function (event) {
    console.log("edge click");
    var edge = event.target;
    var id = edge.data("id");
    var equipment = edge.data("equipment");
    systemType = edge.data("systemtype");

    if (editModeOn) {
      setChecked(systemType, editCheckboxes);

      equipment.forEach((element) => {
        setChecked(`${element.id}`, checkedCheckboxes);
        console.log(element.id);
      });
    } else if (delModeOn) {
      edge.remove();
    }
  });

  canvas.addEventListener("click", (e) => {
    addNodeToGraph(e);
    toolIsSelected = false;
  });
}
function addNodeToGraph(e) {
  if (addNodeOn) {
    var node = [
      {
        data: {
          id: `${nodeId++}`,
          nodetype: nodeType,
          systemtype: "heat",
          length: 0,
          equipment: [
            {
              name: `node ${nodeId}`,
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
  }
}
function addEdgeToGraph(id) {
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
            systemtype: "heat",
            length: 20,
            equipment: [
              {
                name: "edge",
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
    }
  }
}
//конец

const scrollableList = document.getElementById("scrollableList");
loadDataFromServer(getSourceUrl).then((data) => {
  data.forEach((item) => {
    const listItem = document.createElement("div");
    const checkbox = document.createElement("input");
    checkbox.type = "checkbox";
    checkbox.name = "itemCheckbox";
    checkbox.className = "group3";
    checkbox.value = item.id;
    checkbox.id = `${item.id}`;
    console.log(checkbox.id);

    listItem.appendChild(checkbox);
    listItem.appendChild(document.createElement("br"));

    const nameText = document.createTextNode(`Наименование: ${item.name}`);
    listItem.appendChild(nameText);
    listItem.appendChild(document.createElement("br"));

    const throughputText = document.createTextNode(
      `Пропускная сп.: ${item.throughput}`
    );
    listItem.appendChild(throughputText);
    listItem.appendChild(document.createElement("br"));

    const resistanceText = document.createTextNode(
      `Сопротивление: ${item.resistance}`
    );
    listItem.appendChild(resistanceText);
    listItem.appendChild(document.createElement("br"));

    const costText = document.createTextNode(`Кап. затраты: ${item.cost}`);
    listItem.appendChild(costText);

    scrollableList.appendChild(listItem);
  });
});
const checkedCheckboxes = document.querySelectorAll(".group3");
function displaySelectedValues() {
  let selectedValues = [];

  checkedCheckboxes.forEach((checkbox) => {
    const value = checkbox.value;
    selectedValues.push(value);
  });

  console.log(selectedValues);
}

function setChecked(id, checkboxes) {
  checkboxes.forEach((checkbox) => {
    if (checkbox.id === id) {
      console.log(checkbox.id);
      checkbox.checked = true;
    } else {
      checkbox.checked = false;
    }
  });
}
