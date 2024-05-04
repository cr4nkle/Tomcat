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
var nodeId = 0;
var edgeId;
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
    setModeValue(false, false, false, false);
    cy = await initializeEmptyGraph(container);
    initializeGraphMethods(cy);
  } else {
    alert("Ввод отменен");
  }
});

uploadFromPc.addEventListener("change", async function (e) {
  var file = e.target.files[0];
  modelName = file.name;
  var url = URL.createObjectURL(file);
  cy = await initializeGraph(new Request(url), container);
  initializeGraphMethods(cy);
  setModeValue(false, false, false, false);
  // var nodesArray = cy.nodes().toArray();
  // var nodeWithMaxId = null;
  // var maxIdValue = -Infinity;

  // nodesArray.forEach(function (node) {
  //   var idValue = parseInt(node.id().replace(/[^\d]/g, ""), 10);

  //   if (idValue > maxIdValue) {
  //     maxIdValue = idValue;
  //     nodeWithMaxId = node;
  //   }
  // });
  // nodeId = parseInt(nodeWithMaxId.id().replace(/[^\d]/g, ""), 10);
  // console.log(nodeId);
  modelName = removeExtension(modelName);
  alert("Вы открыли модель с названием " + modelName);
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
  // var nodesArray = cy.nodes().toArray();
  // var nodeWithMaxId = null;
  // var maxIdValue = -Infinity;

  // nodesArray.forEach(function (node) {
  //   var idValue = parseInt(node.id().replace(/[^\d]/g, ""), 10);

  //   if (idValue > maxIdValue) {
  //     maxIdValue = idValue;
  //     nodeWithMaxId = node;
  //   }
  // });
  // nodeId = parseInt(nodeWithMaxId.id().replace(/[^\d]/g, ""), 10);
  // console.log(nodeId);
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
    let problem = getProblem(cy);
    let data = JSON.stringify(problem);
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
          cy.nodes('node[system_type = "heat"]').hide();
          cy.edges('edge[system_type = "heat"]').hide();
        } else if (this.id === "checkbox2") {
          cy.nodes('node[system_type = "power"]').hide();
          cy.edges('edge[system_type = "power"]').hide();
        } else if (this.id === "checkbox3") {
          cy.nodes('node[system_type = "fuel"]').hide();
          cy.edges('edge[system_type = "fuel"]').hide();
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
// function getMaxNodeId() {
//   var nodesArray = cy.nodes().toArray();
//   var nodeWithMaxId = null;
//   var maxIdValue = -Infinity;

//   nodesArray.forEach(function (node) {
//     var idValue = parseInt(node.id().replace(/[^\d]/g, ""), 10);

//     if (idValue > maxIdValue) {
//       maxIdValue = idValue;
//       nodeWithMaxId = node;
//     }
//   });
//   var maxId = parseInt(nodeWithMaxId.id().replace(/[^\d]/g, ""), 10);
//   return maxId;
// }
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
 
  var nodesArray = cy.nodes().toArray();
  var nodeWithMaxId = null;
  var maxIdValue = -Infinity;

  nodesArray.forEach(function (node) {
    var idValue = parseInt(node.id().replace(/[^\d]/g, ""), 10);

    if (idValue > maxIdValue) {
      maxIdValue = idValue;
      nodeWithMaxId = node;
    }
  });
  if (nodeWithMaxId !== null) {
    nodeId = parseInt(nodeWithMaxId.id().replace(/[^\d]/g, ""), 10);
    console.log(nodeId);
  }

  // Обработка нажатий на элементы графа
  cy.on("click", "node", function (event) {
    console.log("node click");
    var node = event.target;
    var id = node.data("id");
    var equipment = node.data("equipment");
    systemType = node.data("system_type");
    addEdgeToGraph(id);

    if (editModeOn) {
      setChecked(systemType, editCheckboxes);
      // editCheckboxes.forEach(function (checkbox) {
      //   console.log("dkjd");
      //   checkbox.style.display = "inline-block";
      //   checkbox.style.width = "80px";
      // });

      equipment.forEach((element) => {
        setChecked(`${element.id}`, checkedCheckboxes);
        console.log(element.id);
      });
    } else if (delModeOn) {
      node.remove();
      var nodesArray = cy.nodes().toArray();
      var nodeWithMaxId = null;
      var maxIdValue = -Infinity;

      nodesArray.forEach(function (node) {
        var idValue = parseInt(node.id().replace(/[^\d]/g, ""), 10);

        if (idValue > maxIdValue) {
          maxIdValue = idValue;
          nodeWithMaxId = node;
        }
      });
      nodeId = parseInt(nodeWithMaxId.id().replace(/[^\d]/g, ""), 10);
      console.log(nodeId);
    }
  });

  cy.on("click", "edge", function (event) {
    console.log("edge click");
    var edge = event.target;
    var id = edge.data("id");
    var equipment = edge.data("equipment");
    systemType = edge.data("system_type");

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

  cy.on("click", function (event) {
    if (event && event.position && addNodeOn) {
      var pos = event.position;
      addNodeToGraph(pos);
      toolIsSelected = false;
    }
  });
}
function addNodeToGraph(pos) {
  if (addNodeOn) {
    var node = [
      {
        data: {
          id: `n${nodeId++}`,
          node_type: nodeType,
          system_type: "heat",
          group: "",
          group_name: "",
          equipment: [
            {
              id: 0,
              price: 0.0,
              throughput: 0,
              resistance: 0,
              cost: 0,
              max_gen: 0,
              min_gen: 0,
              load: 0,
              installed: true,
              efficiency: 1,
            },
          ],
        },
        position: { x: pos.x, y: pos.y },
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
            id: `e${parseInt(
              firstNodeSelected.replace(/[^\d]/g, ""),
              10
            )}${parseInt(secondNodeSelected.replace(/[^\d]/g, ""), 10)}`,
            source: firstNodeSelected,
            target: secondNodeSelected,
            system_type: "heat",
            length: 20,
            equipment: [
              {
                name: "edge",
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
        },
      ];
      cy.add(edge);
      clickCount = 0;
      firstNodeSelected = null;
      secondNodeSelected = null;
    }
  }
}

function getProblem(cy) {
  var nodeIds = cy.nodes().map((node) => {
    return node.id();
  });

  var data = { node_id: [], edge_id: [], node: {}, edge: {} };

  nodeIds.forEach((nodeId) => {
    var node = cy.$("#" + nodeId);
    var equipment = node.data("equipment");
    var connectedEdges = node.connectedEdges();
    var nodeIdCopy;
    var edgeIdCopy;
    var edgeIdDuplicate;
    var flag = false;
    var edgeId;

    connectedEdges.forEach(function (edge) {
      var sourceNodeId = edge.source().id();
      var targetNodeId = edge.target().id();

      edgeId = edge.id();
      edgeIdDuplicate = edgeId + "d";
      nodeIdCopy = nodeId;
      if (!data["edge_id"].includes(edgeId)) {
        let eq = edge.data("equipment");
        data["edge_id"].push(edgeId);

        data["edge"][edgeId] = {
          system_type: edge.data("system_type"),
          throughput: eq[0].throughput,
          resistance: eq[0].resistance,
          cost: eq[0].cost,
          length: edge.data("length"),
          source: sourceNodeId,
          target: targetNodeId,
        };
      }

      edgeIdCopy = edgeId;

      equipment.forEach((item) => {
        if (item.installed === false) {
          data["node_id"].push(nodeIdCopy);
          data["node"][nodeIdCopy] = {
            system_type: node.data("system_type"),
            node_type: node.data("node_type"),
            price: item.price,
            cost: item.cost,
            "max-generation": item.max_gen,
            "min-generation": item.min_gen,
            load: item.load,
            installed: item.installed,
            efficiency: item.efficiency,
          };

          if (!data["edge_id"].includes(edgeIdCopy)) {
            data["edge_id"].push(edgeIdCopy);
            data["edge"][edgeIdCopy] = JSON.parse(
              JSON.stringify(data["edge"][edgeId])
            ); //сделали глубокую копию
            data["edge"][edgeIdCopy].source = nodeIdCopy;
          }
          data["edge_id"].push(edgeIdDuplicate);
          data["edge"][edgeIdDuplicate] = data["edge"][edgeIdCopy];

          nodeIdCopy += "c";
          edgeIdCopy += "c";
          edgeIdDuplicate = edgeIdCopy + "d";
          flag = true;
        }
      });
    });

    if (!flag) {
      data["node_id"].push(nodeId);
      data["node"][nodeId] = {
        system_type: node.data("system_type"),
        node_type: node.data("node_type"),
        price: equipment[0].price,
        cost: equipment[0].cost,
        "max-generation": equipment[0].max_gen,
        "min-generation": equipment[0].min_gen,
        load: equipment[0].load,
        installed: equipment[0].installed,
        efficiency: equipment[0].efficiency,
      };
    }
  });

  data["node_id"].push("lim");
  data["edge_id"].push("sign", "lim");
  console.log(data["node_id"]);
  console.log(data["edge_id"]);
  console.log(data);
  return data;
}
//конец

//как-то оптимизировать этот код
const scrollableList1 = document.getElementById("scrollableList1");
loadDataFromServer(getConsumerUrl).then((data) => {
  data.forEach((item) => {
    // console.log(item);
    const listItem = document.createElement("div");
    listItem.style.fontSize = "10px";
    const checkbox = document.createElement("input");
    checkbox.type = "checkbox";
    checkbox.name = "itemCheckbox";
    checkbox.className = "group5";
    checkbox.value = item.id;
    checkbox.id = `${item.id}`;
    // console.log(checkbox.id);

    listItem.appendChild(checkbox);
    listItem.appendChild(document.createElement("br"));

    const nameText = document.createTextNode(`Наименование: ${item.name}`);
    listItem.appendChild(nameText);
    listItem.appendChild(document.createElement("br"));

    const loadText = document.createTextNode(`Потребление: ${item.load}`);
    listItem.appendChild(loadText);

    scrollableList1.appendChild(listItem);
  });
});
const scrollableList2 = document.getElementById("scrollableList2");
loadDataFromServer(getSourceUrl).then((data) => {
  data.forEach((item) => {
    // console.log(item);
    const listItem = document.createElement("div");
    listItem.style.fontSize = "10px";
    const checkbox = document.createElement("input");
    checkbox.type = "checkbox";
    checkbox.name = "itemCheckbox";
    checkbox.className = "group6";
    checkbox.value = item.id;
    checkbox.id = `${item.id}`;
    // console.log(checkbox.id);

    listItem.appendChild(checkbox);
    listItem.appendChild(document.createElement("br"));

    const nameText = document.createTextNode(`Наименование: ${item.name}`);
    listItem.appendChild(nameText);
    listItem.appendChild(document.createElement("br"));

    const maxGenText = document.createTextNode(
      `Генерация(max): ${item.max_gen}`
    );
    listItem.appendChild(maxGenText);
    listItem.appendChild(document.createElement("br"));

    const minGenText = document.createTextNode(
      `Генерация(min): ${item.min_gen}`
    );
    listItem.appendChild(minGenText);
    listItem.appendChild(document.createElement("br"));

    const priceText = document.createTextNode(`За ед. энерг: ${item.price}`);
    listItem.appendChild(priceText);
    listItem.appendChild(document.createElement("br"));

    const costText = document.createTextNode(`Кап. затраты: ${item.cost}`);
    listItem.appendChild(costText);

    scrollableList2.appendChild(listItem);
  });
});
const scrollableList3 = document.getElementById("scrollableList3");
loadDataFromServer(getLinesUrl).then((data) => {
  data.forEach((item) => {
    // console.log(item);
    const listItem = document.createElement("div");
    listItem.style.fontSize = "10px";
    const checkbox = document.createElement("input");
    checkbox.type = "checkbox";
    checkbox.name = "itemCheckbox";
    checkbox.className = "group6";
    checkbox.value = item.id;
    checkbox.id = `${item.id}`;
    // console.log(checkbox.id);

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

    scrollableList3.appendChild(listItem);
  });
});

const checkedCheckboxes = document.querySelectorAll(".group5");
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

// editCheckboxes.forEach((checkbox) => {
//   checkbox.addEventListener("change")
//   if (checkbox.checked) {
//     console.log(checkbox.id);
//   }
// });
