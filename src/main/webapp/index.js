const hostName = `http://${window.location.hostname}`;
const localeUrl = `${hostName}/rest/get/locale`;
let selectedObjectsIds = [];
let isModelSave = true;

// Функция для получения элемента по его id
const getElement = (id) => document.getElementById(id);

// Объект для хранения элементов
const elements = {
  // startModalTitle: getElement("startModalTitle"),
  newModelBtn: getElement("newModelBtn"),
  newModelModalTitle: getElement("newModelModalTitle"),
  newModelNameInput: getElement("newModelNameInput"),
  createModelBtn: getElement("createModelBtn"),
  chooseModelModalTitle: getElement("chooseModelModalTitle"),
  openModelButton: getElement("openModelButton"),

  openFromDeviceBtn: getElement("openFromDeviceBtn"),
  startModal: getElement("startModal"),
  newModelModal: getElement("newModelModal"),
  newModelBtn: getElement("newModelBtn"),
  createModelBtn: getElement("createModelBtn"),
  newModelNameInput: getElement("newModelNameInput"),

  chooseModelModal: getElement("chooseModelModal"),
  resultModal: getElement("resultModal"),
  openChooseModelModalBtn: getElement("openChooseModelModal"),
  openModelBtn: getElement("openModelButton"),

  modelList: getElement("modelList"),

  openNewModelModalToolbarBtn: getElement("openNewModelModalToolbarBtn"),
  uploadToolbarBtn: getElement("uploadToolbarBtn"),
  uploadInput: getElement("upload"),
  openChooseModelModalToolbarBtn: getElement("openChooseModelModalToolbarBtn"),
  saveToDataWarehouseToolbarBtn: getElement("saveToDataWarehouseToolbarBtn"),
  saveToDeviceToolbarBtn: getElement("saveToDeviceToolbarBtn"),
  deleteModelToolbarBtn: getElement("deleteModelToolbarBtn"),

  addEdgeToolbarBtn: getElement("addEdgeToolbarBtn"),
  addNodeToolbarBtn: document.getElementById("addNodeToolbarBtn"),
  editToolbarBtn: document.getElementById("editToolbarBtn"),
  deleteItemToolbarBtn: document.getElementById("deleteItemToolbarBtn"),
  calculateToolbarBtn: document.getElementById("calculateToolbarBtn"),
  moveToolbarBtn: document.getElementById("moveToolbarBtn"),

  sourceToolbarBtn: document.getElementById("sourceToolbarBtn"),
  distributionNodeToolbarBtn: document.getElementById(
    "distributionNodeToolbarBtn"
  ),
  consumerToolbarBtn: document.getElementById("consumerToolbarBtn"),

  messageModal: getElement("messageModal"),
  messageModalTitle: getElement("messageModalTitle"),
  modalText: getElement("messageModalText"),
  messageModalCloseBtn: getElement("messageModalCloseBtn"),

  nodeModal: getElement("nodeModal"),
  nodeModalTitle: getElement("nodeModalTitle"),
  nodeModalCheckboxes: Array.from(
    document.querySelectorAll(".modal_node_block_checkboxes label")
  ),
  nodeModalInf: Array.from(
    document.querySelectorAll(".modal_node_block_inf label")
  ),
  nodeModalSaveBtn: getElement("nodeModalSaveBtn"),
  nodeModalCloseBtn: getElement("nodeModalCloseBtn"),
  selectAllNodeModalCheckbox: getElement("selectAllNodeModalCheckbox"),
  blockNodeModalCheckbox: getElement("blockNodeModalCheckbox"),
  nodeModalList: getElement("nodeModalList"),
  lineBtn: getElement("lineBtn"),
  sourceBtn: getElement("sourceBtn"),
  consumerBtn: getElement("consumerBtn"),

  resultModalTitle: getElement("resultModalTitle"),
  resultModalSaveBtn: getElement("resultModalSaveBtn"),
  resultModalCloseBtn: getElement("resultModalCloseBtn"),
  resultModalText: getElement("resultModalText"),

  waterNodeModalCheckboxText: getElement("waterNodeModalCheckboxText"),
  powerNodeModalCheckboxText: getElement("powerNodeModalCheckboxText"),
  fuelNodeModalCheckboxText: getElement("fuelNodeModalCheckboxText"),
  heatNodeModalCheckboxText: getElement("heatNodeModalCheckboxText"),
  blockNodeModalCheckboxText: getElement("blockNodeModalCheckboxText"),
  selectAllNodeModalCheckboxText: getElement("selectAllNodeModalCheckboxText"),
  cytoscapeContainer: getElement("cy"),
  hideCheckboxes: document.querySelectorAll(
    ".toolbar_list input[type=checkbox]"
  ),
  bullseyeToolbarBtn: getElement("bullseyeToolbarBtn"),
  waterNodeModalCheckbox: getElement("waterNodeModalCheckbox"),
  powerNodeModalCheckbox: getElement("powerNodeModalCheckbox"),
  fuelNodeModalCheckbox: getElement("fuelNodeModalCheckbox"),
  heatNodeModalCheckbox: getElement("heatNodeModalCheckbox"),
};

loadDataFromServer(localeUrl)
  .then((data) => {
    Object.keys(data.toolbarButtons).forEach((key) => {
      const button = elements[key];
      if (button) {
        setTooltipText(button, data.toolbarButtons[key]);
      }
    });

    Object.keys(data.toolbarButtons.operations).forEach((key) => {
      const button = elements[key];
      if (button) {
        setTooltipText(button, data.toolbarButtons.operations[key]);
      }
    });

    Object.keys(data.toolbarButtons.elements).forEach((key) => {
      const button = elements[key];
      if (button) {
        setTooltipText(button, data.toolbarButtons.elements[key]);
      }
    });
    elements.openModelButton.textContent = data.chooseModelModal.button;
    elements.openChooseModelModalBtn.textContent =
      data.startModal.buttons.openDataStorage;
    elements.openFromDeviceBtn.textContent = data.startModal.buttons.openDevice;
    // elements.startModalTitle.textContent = data.startModal.title;
    elements.newModelBtn.textContent = data.startModal.buttons.newModel;

    elements.newModelModalTitle.textContent = data.newModelModal.title;
    elements.newModelNameInput.placeholder = data.newModelModal.placeholder;
    elements.createModelBtn.textContent = data.newModelModal.createModel;

    elements.chooseModelModalTitle.textContent = data.chooseModelModal.title;
    elements.openModelButton.textContent = data.chooseModelModal.button;

    elements.messageModalTitle.textContent = data.messageModal.title;
    elements.messageModalCloseBtn.textContent = data.messageModal.close;

    elements.nodeModalTitle.textContent = data.nodeModal.title;
    elements.nodeModalCheckboxes.forEach((checkbox, index) => {
      checkbox.textContent = Object.values(data.nodeModal.checkboxes)[index];
    });
    elements.nodeModalInf.forEach((label, index) => {
      label.textContent = Object.values(data.nodeModal.inf)[index];
    });
    elements.nodeModalSaveBtn.textContent = data.nodeModal.save;
    elements.nodeModalCloseBtn.textContent = data.nodeModal.close;

    elements.resultModalTitle.textContent = data.resultModal.title;
    elements.resultModalSaveBtn.textContent = data.resultModal.save;
    elements.resultModalCloseBtn.textContent = data.resultModal.close;

    elements.waterNodeModalCheckboxText.textContent =
      data.nodeModal.checkboxes.water;
    elements.powerNodeModalCheckboxText.textContent =
      data.nodeModal.checkboxes.power;
    elements.fuelNodeModalCheckboxText.textContent =
      data.nodeModal.checkboxes.fuel;
    elements.heatNodeModalCheckboxText.textContent =
      data.nodeModal.checkboxes.heat;
    elements.blockNodeModalCheckboxText.textContent =
      data.nodeModal.checkboxes.block;
    elements.selectAllNodeModalCheckboxText.textContent =
      data.nodeModal.checkboxes.all;
  })
  .catch((error) => console.error("Ошибка загрузки перевода:", error));

elements.startModal.showModal();

// Функция showMessage, адаптированная под механизм переключения слушателей
const showMessage = (text) => {
  elements.modalText.textContent = text;
  elements.messageModal.showModal();

  elements.messageModalCloseBtn.addEventListener("click", () => {
    elements.messageModal.close();
  });
};

// Добавление обработчиков событий

const cyProperties = {
  container: elements.cytoscapeContainer,
  layout: { name: "preset" },
};
let cy = null;
let addEdgeOn = false,
  addNodeOn = false,
  editModeOn = false,
  delModeOn = false,
  moveModeOn = true;
let nodeType;
let nodeId = 1;
let edgeId;
let clickCount = 0;
let firstNodeSelected = null,
  secondNodeSelected = null,
  selectedNode = null,
  selectedEdge = null;
let modelName;
var linesData = {};
var sourcesData = {};
var consumersData = {};

//создать новую модель из тулбара
elements.openNewModelModalToolbarBtn.addEventListener("click", () => {
  elements.newModelModal.showModal(); //и открываем ввод названия модели
});

//на стартовом экране нажимаем создать новую
elements.newModelBtn.addEventListener("click", () => {
  elements.startModal.close(); //закрываем стартовое окно
  elements.newModelModal.showModal(); //и открываем ввод названия модели
});

//нажатие кнопки создать модель
elements.createModelBtn.addEventListener("click", async () => {
  if (isModelSave) {
    elements.newModelModal.close();
    modelName = elements.newModelNameInput.value;

    if (modelName) {
      // Если пользователь ввел название, выводим его в диалоговом окне
      showMessage(`Вы ввели: ${modelName}`);
      // Инициализируем пустой граф и применяем к нему методы

      cy = await initializeEmptyGraph(cyProperties);
      initializeGraphMethods(cy);
      isModelSave = false;
    } else {
      // Если пользователь отменил ввод, выводим сообщение
      showMessage("Вы ничего не ввели!!!");

      elements.newModelModal.close();
      elements.newModelNameInput.value = "";
      elements.newModelModal.showModal();
    }

    elements.newModelNameInput.value = "";
  } else {
    showMessage("Вы не сохранили модель!!");
  }
});

//открыть из бд в тулбаре
elements.openChooseModelModalToolbarBtn.addEventListener("click", async () => {
  elements.chooseModelModal.showModal(); //показываем окно с выбором названий
  let url = `${hostName}/rest/get/models`;
  let selectedModel = null;
  const response = await loadDataFromServer(url);
  const modelList = response.name;
  const selectModel = (listItem) => {
    if (selectedModel) {
      selectedModel.classList.remove("selected");
    }
    listItem.classList.add("selected");
    selectedModel = listItem;
  };

  elements.modelList.innerHTML = "";
  elements.chooseModelModal.showModal();

  modelList.forEach((item) => {
    const listItemElement = document.createElement("li");
    listItemElement.className = "modal_choose_model_item";
    listItemElement.textContent = item;
    elements.modelList.appendChild(listItemElement);
  });

  //нажатие на список с моделями
  elements.modelList.addEventListener("click", (event) => {
    const listItem = event.target.closest("li");
    if (!listItem) return;

    selectModel(listItem);
  });

  //нажатие на кнопку для выбора модели
  elements.openModelBtn.addEventListener("click", async () => {
    if (isModelSave) {
      if (selectedModel) {
        cy = null;
        modelName = null;
        showMessage(`Выбранная модель: ${selectedModel.textContent}`);
        elements.chooseModelModal.close();
        // Запрашиваем у пользователя название модели через диалоговое окно
        modelName = selectedModel.textContent;

        if (modelName) {
          // Если пользователь ввел название, выводим его в диалоговом окне
          showMessage(`Вы ввели: ${modelName}`);
        } else {
          // Если пользователь отменил ввод, выводим сообщение
          showMessage("Ввод отменен");
        }
        // Создаем URL для запроса к серверу с именем модели
        let url = `${hostName}/rest/get/model?name=${modelName}`;
        // Инициализируем граф с данными из сервера

        cy = await initializeGraph(new Request(url), cyProperties);
        initializeGraphMethods(cy);
        isModelSave = false;
      } else {
        showMessage("Модель не выбрана");
      }
    } else {
      showMessage("Вы не сохранили модель!!");
    }
  });
});

//нажатие на кнопку открыть из бд со стартового окна
elements.openChooseModelModalBtn.addEventListener("click", async () => {
  elements.startModal.close();
  let url = `${hostName}/rest/get/models`;
  let selectedModel = null;
  const response = await loadDataFromServer(url);
  const modelList = response.name;
  const selectModel = (listItem) => {
    if (selectedModel) {
      selectedModel.classList.remove("selected");
    }
    listItem.classList.add("selected");
    selectedModel = listItem;
  };

  elements.modelList.innerHTML = "";
  elements.chooseModelModal.showModal();

  modelList.forEach((item) => {
    const listItemElement = document.createElement("li");
    listItemElement.className = "modal_choose_model_item";
    listItemElement.textContent = item;
    elements.modelList.appendChild(listItemElement);
  });

  //нажатие на список с моделями
  elements.modelList.addEventListener("click", (event) => {
    const listItem = event.target.closest("li");
    if (!listItem) return;

    selectModel(listItem);
  });

  //нажатие на кнопку для выбора модели
  elements.openModelBtn.addEventListener("click", async () => {
    if (isModelSave) {
      if (selectedModel) {
        showMessage(`Выбранная модель: ${selectedModel.textContent}`);
        elements.chooseModelModal.close();
        // Запрашиваем у пользователя название модели через диалоговое окно
        modelName = selectedModel.textContent;

        if (modelName) {
          // Если пользователь ввел название, выводим его в диалоговом окне
          showMessage(`Вы ввели: ${modelName}`);
        } else {
          // Если пользователь отменил ввод, выводим сообщение
          showMessage("Ввод отменен");
        }
        // Создаем URL для запроса к серверу с именем модели
        let url = `${hostName}/rest/get/model?name=${modelName}`;
        // Инициализируем граф с данными из сервера

        cy = await initializeGraph(new Request(url), cyProperties);
        initializeGraphMethods(cy);
        isModelSave = false;
      } else {
        showMessage("Модель не выбрана");
      }
    } else {
      showMessage("Вы не сохранили модель!!");
    }
  });
});

//открытие модели с компьютера
elements.uploadInput.addEventListener("change", async (event) => {
  if (isModelSave) {
    elements.startModal.close();
    // Получаем выбранный файл
    var file = event.target.files[0];
    if (!file.name.endsWith(".json")) {
      showMessage("Пожалуйста, выберите файл формата JSON!");
      // elements.startModal.showModal();
      return; // Прерываем выполнение, если файл не JSON
    }
    // Устанавливаем имя модели равным имени файла
    modelName = file.name;
    // Создаем URL для доступа к файлу
    let url = URL.createObjectURL(file);
    // Инициализируем граф с данными из файла

    cy = await initializeGraph(new Request(url), cyProperties);
    initializeGraphMethods(cy);
    isModelSave = false;

    // Удаляем расширение из имени файла
    modelName = removeExtension(modelName);
    // Выводим сообщение с именем загруженной модели
    showMessage(`Вы открыли модель: ${modelName}`);
  } else {
    showMessage("Вы не сохранили модель!!");
  }
});

//скачивание файла
elements.saveToDeviceToolbarBtn.addEventListener("click", () => {
  // Вызываем функцию для скачивания графа
  if (cy !== null) {
    // Получаем JSON представление графа
    var graphJSON = cy.json();
    // Преобразуем JSON в строку
    var graphJSONString = JSON.stringify(graphJSON);
    // Создаем Blob с JSON строкой
    var blob = new Blob([graphJSONString], { type: "application/json" });
    // Создаем URL для Blob
    var url = URL.createObjectURL(blob);

    // Создаем ссылку для скачивания
    var link = document.createElement("a");
    link.href = url;
    link.download = modelName;
    // Инициируем скачивание
    link.click();
    isModelSave = true;
  } else {
    showMessage("Модель не создана");
  }
});

//соханяем в базу данных
elements.saveToDataWarehouseToolbarBtn.addEventListener("click", async () => {
  if (cy !== null) {
    let url = `${hostName}/rest/post/save`;
    // Получаем JSON представление графа
    let graphJSON = cy.json();
    // Получаем идентификаторы всех ребер
    let edgeIds = cy.elements("edge").map(function (edge) {
      return edge.id();
    });
    // Получаем идентификаторы всех узлов
    let nodeIds = cy.elements("node").map(function (node) {
      return node.id();
    });
    // Устанавливаем имя модели без расширения
    graphJSON.name = removeExtension(modelName);
    // Добавляем идентификаторы узлов и ребер в JSON
    graphJSON["node_ids"] = nodeIds;
    graphJSON["edge_ids"] = edgeIds;
    // Преобразуем JSON в строку
    console.log(graphJSON);
    let data = JSON.stringify(graphJSON);

    // Отправляем данные на сервер для сохранения модели
    let result = await loadDataOnServer(url, data, "POST");
    isModelSave = true;
    showMessage("Модель сохранена");
    console.log(result);
  }
});
//удаление модели
elements.deleteModelToolbarBtn.addEventListener("click", () => {
  // Создаем URL для запроса к серверу на удаление модели
  let deleteUrl = `${hostName}/rest/delete/model?name=${modelName}`;
  // Вызываем функцию для удаления данных с сервера
  deleteDataFromServer(deleteUrl);
  showMessage(`Модель ${modelName} удалена.`);
});

//выбор отображения
elements.hideCheckboxes.forEach((checkbox) => {
  // Добавляем обработчик события "change" к каждому чекбоксу
  checkbox.addEventListener("change", function () {
    // Проверяем, не равно ли значение переменной cy null
    if (cy !== null) {
      // Проверяем, не отмечен ли чекбокс
      if (!this.checked) {
        // Проверяем id чекбокса и скрываем соответствующие узлы и ребра в зависимости от id
        if (this.id === "heat") {
          // Скрываем узлы и ребра с system_type = "heat"
          cy.nodes('node[system_type = "heat"]').hide();
          cy.edges('edge[system_type = "heat"]').hide();
        } else if (this.id === "power") {
          // Скрываем узлы и ребра с system_type = "power"
          cy.nodes('node[system_type = "power"]').hide();
          cy.edges('edge[system_type = "power"]').hide();
        } else if (this.id === "fuel") {
          // Скрываем узлы и ребра с system_type = "fuel"
          cy.nodes('node[system_type = "fuel"]').hide();
          cy.edges('edge[system_type = "fuel"]').hide();
        } else if (this.id === "cold") {
          // Скрываем узлы и ребра с system_type = "cold"
          cy.nodes('node[system_type = "cold"]').hide();
          cy.edges('edge[system_type = "cold"]').hide();
        } else if (this.id === "other") {
          // Скрываем узлы и ребра с system_type = "other"
          cy.nodes('node[system_type = "other"]').hide();
          cy.edges('edge[system_type = "other"]').hide();
        }
      } else {
        // Если чекбокс отмечен, показываем все скрытые элементы
        if (this.id === "heat") {
          // Открываем узлы и ребра с system_type = "heat"
          cy.nodes('node[system_type = "heat"]').show();
          cy.edges('edge[system_type = "heat"]').show();
        } else if (this.id === "power") {
          // Открываем узлы и ребра с system_type = "power"
          cy.nodes('node[system_type = "power"]').show();
          cy.edges('edge[system_type = "power"]').show();
        } else if (this.id === "fuel") {
          // Открываем узлы и ребра с system_type = "fuel"
          cy.nodes('node[system_type = "fuel"]').show();
          cy.edges('edge[system_type = "fuel"]').show();
        } else if (this.id === "cold") {
          // Открываем узлы и ребра с system_type = "cold"
          cy.nodes('node[system_type = "cold"]').show();
          cy.edges('edge[system_type = "cold"]').show();
        } else if (this.id === "other") {
          // Скрываем узлы и ребра с system_type = "other"
          cy.nodes('node[system_type = "other"]').show();
          cy.edges('edge[system_type = "other"]').show();
        }
      }
    }
  });
});
//---------------------------------------------остановился пока тут
// Операции
const operationButtons = [
  elements.addEdgeToolbarBtn,
  elements.addNodeToolbarBtn,
  elements.editToolbarBtn,
  elements.deleteItemToolbarBtn,
  elements.moveToolbarBtn,
];

function toggleMoveBtn(buttons) {
  let hasSelectedClass = false;

  // Проходим по всем кнопкам и проверяем наличие класса 'selected'
  for (let button of operationButtons) {
    if (button.classList.contains("selected")) {
      hasSelectedClass = true;
      break; // Прекращаем цикл, так как достаточно одной кнопки с классом 'selected'
    }
  }

  // Если ни одна кнопка не имеет класса 'selected', добавляем его к элементу moveToolbarBtn
  if (!hasSelectedClass) {
    elements.moveToolbarBtn.classList.add("selected");
  } else {
    // Если хотя бы одна кнопка имеет класс 'selected', удаляем его у элемента moveToolbarBtn
    elements.moveToolbarBtn.classList.remove("selected");
  }
}
function toggleMode(modeFlag, enable) {
  if (modeFlag === "addNode") {
    addNodeOn = enable;
    delModeOn = false;
    moveModeOn = false;
    addEdgeOn = false;
    editModeOn = false;
  } else if (modeFlag === "addEdge") {
    addEdgeOn = !enable;
    addNodeOn = false;
    delModeOn = false;
    moveModeOn = false;
    editModeOn = false;
  } else if (modeFlag === "editMode") {
    editModeOn = !enable;
    delModeOn = false;
    moveModeOn = false;
    addEdgeOn = false;
    addNodeOn = false;
  } else if (modeFlag === "delMode") {
    delModeOn = !enable;
    moveModeOn = false;
    addEdgeOn = false;
    editModeOn = false;
    addNodeOn = false;
  } else if (modeFlag === "moveMode") {
    moveModeOn = enable;
    editModeOn = false;
    editModeOn = false;
    delModeOn = false;
    addNodeOn = false;
  }
}

function toggleButtons(enable, buttons, modeFlag) {
  removeSelectedClass(buttons);
  buttons.forEach((button) => {
    button.disabled = !enable;
  });
  toggleMode(modeFlag, enable);
}

function toggleSelected(isSelected, element, modeFlag) {
  removeSelectedClass([...operationButtons, ...elementButtons]);
  if (isSelected) {
    element.classList.remove("selected");
  } else {
    element.classList.add("selected");
  }
  toggleMode(modeFlag, isSelected);
}

elements.addEdgeToolbarBtn.addEventListener("click", () => {
  const isSelected = elements.addEdgeToolbarBtn.classList.contains("selected");
  toggleButtons(false, elementButtons, "addEdge");
  toggleSelected(isSelected, elements.addEdgeToolbarBtn, "addEdge");
  toggleMoveBtn(elementButtons);

  selectedNode = null;
  selectedEdge = null;
});

elements.addNodeToolbarBtn.addEventListener("click", () => {
  const isSelected = elements.addNodeToolbarBtn.classList.contains("selected");
  toggleSelected(isSelected, elements.addNodeToolbarBtn, "addNode");
  toggleButtons(!isSelected, elementButtons, "addNode");
  toggleMoveBtn(elementButtons);

  selectedNode = null;
  selectedEdge = null;
});

elements.editToolbarBtn.addEventListener("click", () => {
  const isSelected = elements.editToolbarBtn.classList.contains("selected");
  toggleButtons(false, elementButtons, "editMode");
  toggleSelected(isSelected, elements.editToolbarBtn, "editMode");
  toggleMoveBtn(elementButtons);

  selectedNode = null;
  selectedEdge = null;
});

elements.deleteItemToolbarBtn.addEventListener("click", () => {
  const isSelected =
    elements.deleteItemToolbarBtn.classList.contains("selected");
  toggleButtons(false, elementButtons, "delMode");
  toggleSelected(isSelected, elements.deleteItemToolbarBtn, "delMode");
  toggleMoveBtn(elementButtons);

  selectedNode = null;
  selectedEdge = null;
});

elements.moveToolbarBtn.addEventListener("click", () => {
  const isSelected = elements.moveToolbarBtn.classList.contains("selected");
  toggleButtons(false, elementButtons, "moveMode");
  toggleSelected(isSelected, elements.moveToolbarBtn, "moveMode");

  selectedNode = null;
  selectedEdge = null;
});

//переход к модели
elements.bullseyeToolbarBtn.addEventListener("click", () => {
  if (cy !== null) cy.fit();
});

//Операция расчет
let result;

elements.calculateToolbarBtn.addEventListener("click", async () => {
  // Проверяем, что граф инициализирован
  if (cy !== null) {
    // Получаем все видимые элементы графа (узлы и ребра)
    var visibleElements = cy.elements().filter(function (element) {
      return element.style("display") !== "none";
    });

    const url = `${hostName}/rest/post/calculate?type=nonlinear`;
    // Получаем данные проблемы для расчета
    // let problem = getLinearProblem(visibleElements);
    let problem = getNonLinearProblem(visibleElements);

    // Преобразуем данные проблемы в JSON строку
    let data = JSON.stringify(problem);
    // Отправляем данные на сервер для расчета
    result = await loadDataOnServer(url, data, "POST");
    // Выводим результат расчета в консоль
    console.log(result);

    const table = createResultTable(result);
    const objectiveText = document.createElement("p");
    objectiveText.textContent = `Значение функции = ${result.objective}`;

    elements.resultModalText.innerHTML = "";
    elements.resultModalText.appendChild(objectiveText);
    elements.resultModalText.appendChild(table);
    elements.resultModal.showModal();
  }
});

elements.resultModalCloseBtn.addEventListener("click", () => {
  elements.resultModal.close();
});

elements.resultModalSaveBtn.addEventListener("click", () => {
  downloadFile(result.data, modelName, "dat");
  downloadFile(result.model, modelName, "mod");
  downloadFile(result.run, modelName, "run");

  elements.resultModal.close();
});

//кнопка сохранить на окне резльтаты расчётов
function downloadFile(data, modelName, extension) {
  var blob = new Blob([data], { type: "application/octet-stream" });
  var url = URL.createObjectURL(blob);

  var link = document.createElement("a");
  link.href = url;
  link.download = `${modelName}.${extension}`;
  link.style.display = "none"; // Скрываем элемент <a>, чтобы он не отображался на странице

  document.body.appendChild(link); // Добавляем элемент <a> в документ
  link.click(); // Запускаем скачивание файла

  setTimeout(() => {
    document.body.removeChild(link); // Удаляем элемент <a> после скачивания файла
    URL.revokeObjectURL(url); // Освобождаем память
  }, 100);

  // downloadLinks.push(link); // Добавляем ссылку на элемент <a> в массив
}

// Функция для очистки массива downloadLinks, если необходимо
function clearDownloadLinks() {
  downloadLinks.forEach((link) => {
    document.body.removeChild(link);
  });
  downloadLinks = []; // Очищаем массив
}

function createResultTable(result) {
  const statement = result.statement;
  const goal = statement.goal.slice(1);
  const matrix = statement.matrix.map((row) => row.slice(1));
  const max = statement.max;
  const min = statement.min;
  const limits = statement.limits;
  const sign = statement.sign;
  const type = statement.type;
  const variables = result.variables;

  const table = document.createElement("table");
  const numColumns = matrix[0].length;
  const headerCells = Array.from({ length: numColumns }, (_, i) => `x${i + 1}`);
  const headerRow = document.createElement("tr");

  headerCells.forEach((headerCell) => {
    const th = document.createElement("th");
    th.textContent = headerCell;
    headerRow.appendChild(th);
  });
  table.appendChild(headerRow);

  const goalRow = document.createElement("tr");
  goal.forEach((goalValue) => {
    const td = document.createElement("td");
    td.textContent = goalValue;
    goalRow.appendChild(td);
  });
  table.appendChild(goalRow);

  const rRows = matrix.map((row, rowIndex) => {
    const rRow = document.createElement("tr");
    row.forEach((cell, cellIndex) => {
      const td = document.createElement("td");
      td.textContent = `${cell}`;
      rRow.appendChild(td);
    });
    rRow.appendChild(
      document.createTextNode(
        `${sign[rowIndex] === 1 ? "=<" : "="} ${limits[rowIndex]}`
      )
    );

    return rRow;
  });

  rRows.forEach((rRow) => table.appendChild(rRow));

  const typeRow = document.createElement("tr");
  type.forEach((typeValue, index) => {
    const td = document.createElement("td");
    td.textContent = typeValue ? "int" : "real";
    typeRow.appendChild(td);
  });
  table.appendChild(typeRow);

  const maxRow = document.createElement("tr");
  max.forEach((maxValue) => {
    const td = document.createElement("td");
    td.textContent = maxValue;
    maxRow.appendChild(td);
  });
  table.appendChild(maxRow);

  const minRow = document.createElement("tr");
  min.forEach((minValue) => {
    const td = document.createElement("td");
    td.textContent = minValue;
    minRow.appendChild(td);
  });
  table.appendChild(minRow);

  const variablesRow = document.createElement("tr");
  variables.forEach((variable) => {
    const td = document.createElement("td");
    td.textContent = variable.toFixed(2);
    variablesRow.appendChild(td);
  });
  table.appendChild(variablesRow);

  return table;
}

//Элементы
const elementButtons = [
  elements.sourceToolbarBtn,
  elements.distributionNodeToolbarBtn,
  elements.consumerToolbarBtn,
];

elements.sourceToolbarBtn.addEventListener("click", () => {
  if (elements.addNodeToolbarBtn.classList.contains("selected")) {
    removeSelectedClass(elementButtons);
    elements.sourceToolbarBtn.classList.add("selected");
    // Устанавливаем тип узла как источник
    nodeType = "source";
  }
});

elements.distributionNodeToolbarBtn.addEventListener("click", () => {
  if (elements.addNodeToolbarBtn.classList.contains("selected")) {
    removeSelectedClass(elementButtons);
    elements.distributionNodeToolbarBtn.classList.add("selected");
    // Устанавливаем тип узла как коннектор
    nodeType = "connector";
  }
});

elements.consumerToolbarBtn.addEventListener("click", () => {
  if (elements.addNodeToolbarBtn.classList.contains("selected")) {
    removeSelectedClass(elementButtons);
    elements.consumerToolbarBtn.classList.add("selected");
    // Устанавливаем тип узла как потребитель
    nodeType = "consumer";
  }
});
//----------------------------------------------

//кнопка закрыть на окне информация об оборудовании
elements.nodeModalCloseBtn.addEventListener("click", () => {
  const systemTypesCheckboxes = [
    elements.powerNodeModalCheckbox,
    elements.heatNodeModalCheckbox,
    elements.fuelNodeModalCheckbox,
    elements.waterNodeModalCheckbox,
  ];

  setCheckedSystemTypesCheckboxes("", systemTypesCheckboxes);
  elements.nodeModal.close();
  elements.selectAllNodeModalCheckbox.checked = false;
  elements.blockNodeModalCheckbox.checked = false;
});

//кнопка закрыть на окне информация об оборудовании
elements.nodeModalSaveBtn.addEventListener("click", () => {
  let systemType = getSystemTypeFromCheckboxes();
  console.log(systemType);

  //Проверяем, что граф инициализирован
  if (cy !== null) {
    // Проверяем, что выбран узел или ребро
    if (selectedNode !== null) {
      // Получаем тип выбранного узла
      var type = selectedNode.data("node_type");

      // Обработка для узла-источника
      if (type === "source") {
        saveSelectedData(selectedNode, sourcesData);
      }
      // Обработка для узла-потребителя
      else if (type === "consumer") {
        saveSelectedData(selectedNode, consumersData);
      }

      selectedNode.data("system_type", systemType);
      if (elements.blockNodeModalCheckbox.checked) {
        selectedNode.lock();
      } else {
        selectedNode.unlock();
      } //добавляет значение чекбокса к атрибуту графа
    }
    // Обработка для выбранного ребра
    else if (selectedEdge !== null) {
      saveSelectedData(selectedEdge, linesData);
      selectedEdge.data("system_type", systemType);
      selectedEdge.locked(elements.blockNodeModalCheckbox.checked);
      console.log(elements.blockNodeModalCheckbox.checked);
    }
  }

  var ids = findSystemTypes(cy);
  setCheckedByIds(ids, elements.hideCheckboxes);

  elements.nodeModal.close();
  elements.selectAllNodeModalCheckbox.checked = false;
  elements.blockNodeModalCheckbox.checked = false;
});

function getSystemTypeFromCheckboxes() {
  const systemTypesCheckboxes = [
    elements.powerNodeModalCheckbox,
    elements.heatNodeModalCheckbox,
    elements.fuelNodeModalCheckbox,
    elements.waterNodeModalCheckbox,
  ];

  const selectedCheckbox = systemTypesCheckboxes.find(
    (checkbox) => checkbox.checked
  );

  setCheckedSystemTypesCheckboxes("", systemTypesCheckboxes);

  switch (selectedCheckbox.id) {
    case "powerNodeModalCheckbox":
      return "power";
    case "heatNodeModalCheckbox":
      return "heat";
    case "fuelNodeModalCheckbox":
      return "fuel";
    case "waterNodeModalCheckbox":
      return "cold";
    default:
      return "other";
  }
}

function saveSelectedData(selectedElement, objectsData) {
  const checkboxes = document.querySelectorAll(
    '.modal_node_item input[type="checkbox"]'
  ); //чекбоксы с выбора оборудования
  // Фильтруем выбранные чекбоксы
  const selectedCheckboxes = Array.from(checkboxes).filter(
    (checkbox) => checkbox.checked
  );
  // Получаем идентификаторы выбранных чекбоксов
  var selectedIds = getSelectedIds(selectedCheckboxes);
  // Получаем данные оборудования по идентификаторам
  const selectedData = selectedIds.map((id) => {
    return objectsData[id];
  });
  // Получаем текущие данные
  var elementData = selectedElement.data();

  // Очищаем текущие данные оборудования
  elementData.equipment.length = 0;

  // Добавляем выбранные данные оборудования в элемент'
  selectedData.forEach((data) => {
    elementData.equipment.push(data);
  });

  // Обновляем данные элемента в графе
  selectedElement.data(elementData);
  console.log(selectedElement.data());
}

//заблокировать чекбокс
elements.blockNodeModalCheckbox.addEventListener("change", function () {
  const checkboxes = document.querySelectorAll(
    '.modal_node_item input[type="checkbox"]'
  );
  elements.selectAllNodeModalCheckbox.disabled = this.checked;
  checkboxes.forEach((checkbox) => {
    checkbox.disabled = this.checked;
  });
});

//обработка изменений в выборе оборудования
elements.selectAllNodeModalCheckbox.addEventListener("change", function () {
  const checkboxes = document.querySelectorAll(
    '.modal_node_item input[type="checkbox"]'
  );

  checkboxes.forEach((checkbox) => {
    checkbox.checked = this.checked;
  });
});

// Функция для поиска уникальных типов систем в графе Cytoscape
function findSystemTypes(cy) {
  // Получаем все элементы графа и извлекаем их типы систем
  var allSystemTypes = cy
    .elements()
    .map(function (element) {
      return element.data("system_type");
    })
    // Фильтруем, чтобы оставить только те типы систем, которые не равны undefined
    .filter(function (systemType) {
      return systemType !== undefined;
    });

  // Создаем Set из всех типов систем для удаления дубликатов
  var uniqueSystemTypes = new Set(allSystemTypes);
  // Преобразуем Set обратно в массив для удобства использования
  var uniqueSystemTypesArray = Array.from(uniqueSystemTypes);

  // Возвращаем массив уникальных типов систем
  return uniqueSystemTypesArray;
}

// Функция для установки чекбоксов в состояние "отмечено" или "не отмечено" в зависимости от их идентификаторов
function setCheckedByIds(ids, checkboxes) {
  // Перебираем каждый чекбокс в переданном массиве
  checkboxes.forEach(function (checkbox) {
    // Проверяем, содержится ли идентификатор текущего чекбокса в переданном массиве идентификаторов
    if (ids.includes(checkbox.id)) {
      // Если содержится, устанавливаем чекбокс в состояние "отмечено"
      checkbox.checked = true;
    } else {
      // Если не содержится, устанавливаем чекбокс в состояние "не отмечено"
      checkbox.checked = false;
    }
  });
}

// Функция для загрузки данных с сервера с использованием XMLHttpRequest
function loadDataFromServer(url) {
  return new Promise((resolve, reject) => {
    let xhr = new XMLHttpRequest();

    // Открываем соединение с сервером для GET-запроса
    xhr.open("GET", url, true);
    // Устанавливаем заголовок запроса, указывая, что тип контента - JSON
    xhr.setRequestHeader("Content-Type", "application/json");
    // Обработчик события изменения состояния запроса
    xhr.onreadystatechange = function () {
      // Проверяем, что запрос завершен
      if (xhr.readyState === 4) {
        // Проверяем статус ответа
        if (xhr.status === 200) {
          // Парсим ответ в JSON и разрешаем промис с полученными данными
          let responseArray = JSON.parse(xhr.responseText);
          resolve(responseArray);
        } else {
          // Если статус ответа не 200, отклоняем промис с ошибкой
          reject(new Error("Ошибка запроса: " + xhr.status));
        }
      }
    };
    // Отправляем запрос
    xhr.send();
  });
}

// Функция для отправки данных на сервер с использованием XMLHttpRequest
function loadDataOnServer(url, data, method) {
  return new Promise((resolve, reject) => {
    let xhr = new XMLHttpRequest();

    // Открываем соединение с сервером для POST-запроса (или другого метода)
    xhr.open(method, url, true);
    // Устанавливаем заголовок запроса, указывая, что тип контента - JSON
    xhr.setRequestHeader("Content-Type", "application/json");
    // Обработчик события изменения состояния запроса
    xhr.onreadystatechange = function () {
      // Проверяем, что запрос завершен
      if (xhr.readyState === 4) {
        // Проверяем статус ответа
        if (xhr.status === 200) {
          // Парсим ответ в JSON и разрешаем промис с полученными данными
          let responseArray = JSON.parse(xhr.responseText);
          resolve(responseArray);
        } else {
          // Если статус ответа не 200, отклоняем промис с ошибкой
          reject(new Error("Ошибка запроса: " + xhr.status));
        }
      }
    };
    // Отправляем запрос с данными
    xhr.send(data);
  });
}

// Функция для удаления данных с сервера с использованием XMLHttpRequest
function deleteDataFromServer(url) {
  let xhr = new XMLHttpRequest();
  // Открываем соединение с сервером для DELETE-запроса
  xhr.open("DELETE", url, true);
  // Устанавливаем заголовок запроса, указывая, что тип контента - JSON
  xhr.setRequestHeader("Content-Type", "application/json");
  // Обработчик события изменения состояния запроса
  xhr.onreadystatechange = function () {
    // Проверяем, что запрос завершен
    if (xhr.readyState === 4) {
      // Проверяем статус ответа
      if (xhr.status === 200) {
        // Парсим ответ в JSON и выводим его в консоль
        let responseArray = JSON.parse(xhr.responseText);
        console.log(responseArray);
      } else {
        // Если статус ответа не 200, выводим ошибку в консоль
        console.log(new Error("Ошибка запроса: " + xhr.status));
      }
    }
  };
  // Отправляем запрос
  xhr.send();
}

// Функция для получения максимального идентификатора узла в графе Cytoscape
function getMaxNodeId(cy) {
  // Получаем массив всех узлов в графе
  var nodesArray = cy.nodes().toArray();
  var nodeWithMaxId = null;
  var maxIdValue = -Infinity;

  // Перебираем каждый узел в массиве
  nodesArray.forEach(function (node) {
    // Извлекаем числовое значение идентификатора узла, удаляя все нечисловые символы
    var idValue = parseInt(node.id().replace(/[^\d]/g, ""), 10);

    // Если текущий идентификатор больше максимального, обновляем максимальное значение и узел
    if (idValue > maxIdValue) {
      maxIdValue = idValue;
      nodeWithMaxId = node;
    }
  });

  var maxId;
  // Если был найден узел с максимальным идентификатором, используем его идентификатор
  if (nodeWithMaxId !== null) {
    maxId = parseInt(nodeWithMaxId.id().replace(/[^\d]/g, ""), 10);
  } else {
    // Если узлов нет, возвращаем начальное значение 1
    maxId = 1;
  }
  // Возвращаем максимальный идентификатор узла
  return maxId;
}

// Функция для инициализации графа с данными, полученными из запроса
function initializeGraph(request, properties) {
  let url = `${hostName}/rest/get/style`;
  // Возвращаем промис, который будет разрешен с объектом графа Cytoscape
  return new Promise((resolve, reject) => {
    // Используем Promise.all для параллельного выполнения двух асинхронных запросов
    Promise.all([
      // Первый запрос: получаем данные графа из запроса
      fetch(request).then((res) => {
        return res.json();
      }),
      // Второй запрос: загружаем стили графа с сервера
      loadDataFromServer(url).then((res) => {
        return res;
      }),
    ])
      .then((dataArray) => {
        // После успешного выполнения обоих запросов создаем объект графа Cytoscape
        const cy = cytoscape({
          container: properties.container, // Контейнер для графа
          style: dataArray[1].style, // Стили графа
          elements: dataArray[0].elements, // Элементы графа
          layout: properties.layout, // Конфигурация расположения элементов
        });
        // Разрешаем промис с объектом графа
        resolve(cy);
      })
      .catch((error) => {
        // Если произошла ошибка, отклоняем промис с ошибкой
        reject(error);
      });
  });
}

// Функция для инициализации пустого графа
function initializeEmptyGraph(properties) {
  let url = `${hostName}/rest/get/style`;
  // Возвращаем промис, который будет разрешен с объектом графа Cytoscape
  return new Promise((resolve, reject) => {
    // Используем Promise.all для выполнения асинхронного запроса загрузки стилей графа
    Promise.all([
      loadDataFromServer(url).then((res) => {
        return res;
      }),
    ])
      .then((dataArray) => {
        console.log(dataArray[0]);
        // Создаем объект графа Cytoscape без элементов
        const cy = cytoscape({
          container: properties.container, // Контейнер для графа
          style: dataArray[0].style, // Стили графа
          elements: [], // Пустой массив элементов
          layout: properties.layout, // Конфигурация расположения элементов
        });
        // Разрешаем промис с объектом графа
        resolve(cy);
      })
      .catch((error) => {
        // Если произошла ошибка, отклоняем промис с ошибкой
        reject(error);
      });
  });
}

function removeExtension(filename) {
  return filename.substring(0, filename.lastIndexOf(".")) || filename;
}

function removeSelectedClass(buttons) {
  buttons.forEach((button) => button.classList.remove("selected"));
}

function setTooltipText(element, text) {
  const tooltipElement = element.querySelector(".tooltiptext");
  if (!tooltipElement) return;

  tooltipElement.textContent = text;
}

function createNodeElementLineItem(obj) {
  const element = document.createElement("li");
  element.className = "modal_node_item";
  element.innerHTML = `
    <p>Наименование: ${obj.name}</p>
    <p>Пропускная способность: ${obj.throughput}</p>
    <p>Сопротивление: ${obj.resistance}</p>
    <p>Цена: ${obj.cost}</p>
    <label><input type="checkbox" value="${obj.id}" /> Выбрать</label>
    
  `;

  return element;
}

function createNodeElementSourceItem(obj) {
  const element = document.createElement("li");
  element.className = "modal_node_item";
  element.innerHTML = `
    <p>Наименование: ${obj.name}</p>
    <p>Максимальная производительность: ${obj.max_gen}</p>
    <p>Минимальная производительность: ${obj.min_gen}</p>
    <p>Цена: ${obj.price}</p>
    <p>Затраты: ${obj.cost}</p>
    <p>Эффективность: ${obj.efficiency}</p>
    <label><input type="checkbox" value="${obj.id}" /> Выбрать</label>
  `;

  return element;
}

function createNodeElementConsumerItem(obj) {
  const element = document.createElement("li");
  element.className = "modal_node_item";
  element.innerHTML = `
    <p>Наименование: ${obj.name}</p>
    <p>Нагрузка: ${obj.load}</p>
    <label><input type="checkbox" value="${obj.id}" /> Выбрать</label>
  `;

  return element;
}

//код нужный для визуализации

//функции для работы приложения

// Функция для инициализации методов работы с графом в Cytoscape
function initializeGraphMethods(cy) {
  // Устанавливаем начальный масштаб и позицию камеры
  cy.fit();
  // Получаем максимальный идентификатор узла в графе
  nodeId = getMaxNodeId(cy);
  // Устанавливаем начальные значения режимов работы
  // Получаем уникальные типы систем в графе и устанавливаем чекбоксы в соответствии с этими типами
  var ids = findSystemTypes(cy);
  setCheckedByIds(ids, elements.hideCheckboxes);

  // Обработчик события клика по узлу
  cy.on("click", "node", async function (event) {
    console.log("node click");
    var node = event.target;
    var id = node.data("id");
    addEdgeToGraph(id);

    if (editModeOn) {
      selectedNode = node;
      selectedEdge = null;
      await handleNodeClick(node);
    } else if (delModeOn) {
      node.remove();
      nodeId = getMaxNodeId(cy);
      var ids = findSystemTypes(cy);
      setCheckedByIds(ids, elements.hideCheckboxes);
    }
  });

  // Обработчик события клика по ребру
  cy.on("click", "edge", async function (event) {
    console.log("edge click");
    var edge = event.target;

    if (editModeOn) {
      selectedNode = null;
      selectedEdge = edge;
      await handleEdgeClick(edge);
    } else if (delModeOn) {
      edge.remove();
    }
  });

  // Обработчик события клика по области графа
  cy.on("click", function (event) {
    if (event && event.position && addNodeOn) {
      var pos = event.position;
      addNodeToGraph(pos);
    }
  });
}

// Функция для обработки клика по узлу
async function handleNodeClick(node) {
  let equipment = node.data("equipment");
  let systemType = node.data("system_type");
  let nodeType = node.data("node_type");
  let isLocked = node.locked();
  let fill;

  if (nodeType === "source") {
    fill = async (systemType) => {
      let url = `${hostName}/rest/get/sources?type=${systemType}`;
      const data = await loadDataFromServer(url);

      // Показать элементы
      elements.nodeModalList.style.display = "";
      elements.selectAllNodeModalCheckboxText.style.display = "";
      elements.selectAllNodeModalCheckbox.style.display = "";

      elements.nodeModalList.innerHTML = "";

      data.forEach((item) => {
        const nodeElement = createNodeElementSourceItem(item);
        nodeModalList.appendChild(nodeElement);
        sourcesData[item.id] = {
          id: item.id,
          name: item.name,
          max_gen: item.max_gen,
          min_gen: item.min_gen,
          price: item.price,
          cost: item.cost,
          efficiency: item.efficiency,
          installed: false,
        };
      });
    };
  } else if (nodeType === "consumer") {
    fill = async (systemType) => {
      let url = `${hostName}/rest/get/consumers?type=${systemType}`;
      const data = await loadDataFromServer(url);

      // Показать элементы
      elements.nodeModalList.style.display = "";
      elements.selectAllNodeModalCheckboxText.style.display = "";
      elements.selectAllNodeModalCheckbox.style.display = "";

      elements.nodeModalList.innerHTML = "";

      data.forEach((item) => {
        const nodeElement = createNodeElementConsumerItem(item);
        nodeModalList.appendChild(nodeElement);
        consumersData[item.id] = {
          id: item.id,
          name: item.name,
          load: item.load,
          installed: false,
        };
      });
    };
  } else if (nodeType === "connector") {
    fill = async (systemType) => {};
    elements.nodeModalList.style.display = "none";
    elements.selectAllNodeModalCheckboxText.style.display = "none";

    if (
      elements.selectAllNodeModalCheckbox &&
      elements.selectAllNodeModalCheckbox.parentNode
    ) {
      elements.selectAllNodeModalCheckbox.style.display = "none";
    }
  }

  await fill(systemType);
  elements.selectAllNodeModalCheckbox.disabled = false;
  elements.blockNodeModalCheckbox.checked = isLocked;
  elements.nodeModal.showModal();
  handleReloadData(fill, node);
}

function handleReloadData(fill, element) {
  let systemType = element.data("system_type");
  let equipment = element.data("equipment");

  const itemCheckbox = document.querySelectorAll(
    ".modal_node_item input[type=checkbox]"
  );

  updateCheckboxesState(equipment, itemCheckbox);

  const systemTypesCheckboxes = [
    elements.powerNodeModalCheckbox,
    elements.heatNodeModalCheckbox,
    elements.fuelNodeModalCheckbox,
    elements.waterNodeModalCheckbox,
  ];

  let lastCheckedCheckbox = setCheckedSystemTypesCheckboxes(
    systemType,
    systemTypesCheckboxes
  ); // Переменная для отслеживания последнего выбранного чекбокса

  // Функция для обработки выбора чекбокса
  const handleCheckboxChange = (checkbox) => {
    if (lastCheckedCheckbox) {
      // Снимаем выбор с предыдущего чекбокса
      lastCheckedCheckbox.checked = false;
    }

    // Устанавливаем выбор на текущий чекбокс
    checkbox.checked = true;
    lastCheckedCheckbox = checkbox; // Обновляем последний выбранный чекбокс
  };

  // Пример использования для каждого чекбокса
  systemTypesCheckboxes.forEach((checkbox) => {
    checkbox.addEventListener("change", async () => {
      switch (checkbox.id) {
        case "powerNodeModalCheckbox":
          systemType = "power";
          break;
        case "heatNodeModalCheckbox":
          systemType = "heat";
          break;
        case "fuelNodeModalCheckbox":
          systemType = "fuel";
          break;
        case "waterNodeModalCheckbox":
          systemType = "cold";
          break;
        default:
          systemType = "other";
      }
      handleCheckboxChange(checkbox);
      await fill(systemType);
      elements.blockNodeModalCheckbox.checked = false;
      elements.selectAllNodeModalCheckbox.disabled = false;
      element.unlock();
      // updateCheckboxesState(equipment, itemCheckbox);
    });
  });
}

// Функция для обработки клика по ребру
async function handleEdgeClick(edge) {
  let systemType = edge.data("system_type");
  let isLocked = edge.locked();
  console.log(isLocked);

  const fill = async (systemType) => {
    let url = `${hostName}/rest/get/lines?type=${systemType}`;
    const data = await loadDataFromServer(url);

    elements.nodeModalList.innerHTML = "";

    data.forEach((item) => {
      const nodeElement = createNodeElementLineItem(item);
      nodeModalList.appendChild(nodeElement);
      linesData[item.id] = {
        id: item.id,
        name: item.name,
        throughput: item.throughput,
        resistance: item.resistance,
        cost: item.cost,
        installed: false,
      };
    });
  };

  await fill(systemType);
  handleReloadData(fill, edge);
  elements.nodeModal.showModal();
  elements.selectAllNodeModalCheckbox.disabled = false;
}

// Функция для обновления состояния чекбоксов
function updateCheckboxesState(equipment, checkboxes) {
  equipment.forEach((element) => {
    const checkbox = Array.from(checkboxes).find(
      (cb) => cb.value === `${element.id}`
    );
    if (checkbox) {
      checkbox.checked = true;
    }
  });
}

// Функция для добавления узла в граф
function addNodeToGraph(pos) {
  // Проверяем, разрешено ли добавление узла
  if (addNodeOn) {
    let id = `n${nodeId++}`;
    // Создаем новый узел с уникальным идентификатором, типом узла, типом системы и начальными данными оборудования
    let node = [
      {
        data: {
          id: id,
          node_type: nodeType,
          system_type: "heat",
          grouped: "false",
          group_name: "",
          name: id,
          equipment: [
            {
              id: 0,
              name: "",
              price: 0.0,
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
    // Добавляем новый узел в граф
    cy.add(node);
    // Получаем уникальные типы систем в графе и устанавливаем чекбоксы в соответствии с этими типами
    var ids = findSystemTypes(cy);
    setCheckedByIds(ids, elements.hideCheckboxes);
  }
}

// Функция для добавления ребра в граф
function addEdgeToGraph(id) {
  // Проверяем, разрешено ли добавление ребра
  if (addEdgeOn) {
    // Проверяем, какой шаг добавления ребра (первый или второй узел выбран)
    if (clickCount == 0) {
      // Если это первый выбранный узел, сохраняем его идентификатор
      firstNodeSelected = id;
      clickCount++;
    } else if (clickCount == 1) {
      // Если это второй выбранный узел, создаем ребро между первым и вторым узлами
      secondNodeSelected = id;
      let edgeId = `e${parseInt(
        firstNodeSelected.replace(/[^\d]/g, ""),
        10
      )}${parseInt(secondNodeSelected.replace(/[^\d]/g, ""), 10)}`;
      var edge = [
        {
          data: {
            id: edgeId,
            source: firstNodeSelected,
            target: secondNodeSelected,
            system_type: "heat",
            name: edgeId,
            length: 20,
            equipment: [
              {
                id: 0,
                name: "",
                throughput: 0,
                resistance: 0,
                cost: 0,
                installed: true,
              },
            ],
          },
        },
      ];
      // Добавляем ребро в граф
      cy.add(edge);
      // Получаем уникальные типы систем в графе и устанавливаем чекбоксы в соответствии с этими типами
      var ids = findSystemTypes(cy);
      setCheckedByIds(ids, elements.hideCheckboxes);
      // Сбрасываем счетчик кликов и очищаем выбранные узлы
      clickCount = 0;
      firstNodeSelected = null;
      secondNodeSelected = null;
    }
  }
}

// Функция для получения данных о проблеме из видимых элементов графа
function getLinearProblem(visibleElements) {
  // Фильтруем видимые элементы, оставляя только узлы
  var nodes = visibleElements.filter(function (element) {
    return element.isNode();
  });
  // Инициализируем объект для хранения данных о проблеме
  var data = { node_id: [], edge_id: [], node: {}, edge: {} };

  // Перебираем каждый узел
  nodes.forEach((node) => {
    let nodeId = node.id();
    let equipment = node.data("equipment");
    let connectedEdges = node.connectedEdges();
    let nodeIdCopy;
    let flag = false;

    // Перебираем все ребра, связанные с текущим узлом
    connectedEdges.forEach(function (edge) {
      var sourceNodeId = edge.source().id();
      var targetNodeId = edge.target().id();
      let isSource = function () {
        return nodeId === sourceNodeId;
      };

      let edgeId = edge.id();
      let edgeIdDuplicate = edgeId + "d";
      let edgeIdCopy = edgeId;

      // Проверяем, нет ли уже такого идентификатора ребра в данных
      if (isSource()) {
        nodeIdCopy = sourceNodeId;
        if (!data["edge_id"].includes(edgeId)) {
          let eq = edge.data("equipment");
          data["edge_id"].push(edgeId);

          // Добавляем данные о ребре в объект data
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
        // Перебираем все элементы оборудования узла
        equipment.forEach((item) => {
          // Если оборудование не установлено
          if (!item.installed) {
            if (!data["node_id"].includes(nodeIdCopy)) {
              data["node_id"].push(nodeIdCopy);
              // Добавляем данные об узле в объект data
              data["node"][nodeIdCopy] = {
                system_type: node.data("system_type"),
                node_type: node.data("node_type"),
                price: item.price,
                cost: item.cost,
                max_generation: item.max_gen,
                min_generation: item.min_gen,
                load: item.load,
                installed: item.installed,
                efficiency: item.efficiency,
              };
            }
            // Если ребро еще не добавлено, добавляем его
            if (!data["edge_id"].includes(edgeIdCopy)) {
              data["edge_id"].push(edgeIdCopy);
              // Создаем глубокую копию данных о ребре
              data["edge"][edgeIdCopy] = JSON.parse(
                JSON.stringify(data["edge"][edgeId])
              );
              data["edge"][edgeIdCopy].source = nodeIdCopy;
            }
            // Добавляем дубликат ребра с дополнительным идентификатором
            data["edge_id"].push(edgeIdDuplicate);
            data["edge"][edgeIdDuplicate] = data["edge"][edgeIdCopy];

            // Изменяем идентификаторы для следующих элементов
            nodeIdCopy += "c";
            edgeIdCopy += "c";
            edgeIdDuplicate = edgeIdCopy + "d";
            flag = true;
          }
        });
      } else {
        nodeIdCopy = targetNodeId;
        if (!data["edge_id"].includes(edgeId)) {
          let eq = edge.data("equipment");
          data["edge_id"].push(edgeId);

          // Добавляем данные о ребре в объект data
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
        // Перебираем все элементы оборудования узла
        equipment.forEach((item) => {
          // Если оборудование не установлено
          if (!item.installed) {
            if (!data["node_id"].includes(nodeIdCopy)) {
              data["node_id"].push(nodeIdCopy);
              // Добавляем данные об узле в объект data
              data["node"][nodeIdCopy] = {
                system_type: node.data("system_type"),
                node_type: node.data("node_type"),
                price: item.price,
                cost: item.cost,
                max_generation: item.max_gen,
                min_generation: item.min_gen,
                load: item.load,
                installed: item.installed,
                efficiency: item.efficiency,
              };
            }

            // Если ребро еще не добавлено, добавляем его
            if (!data["edge_id"].includes(edgeIdCopy)) {
              data["edge_id"].push(edgeIdCopy);
              // Создаем глубокую копию данных о ребре
              data["edge"][edgeIdCopy] = JSON.parse(
                JSON.stringify(data["edge"][edgeId])
              );
              data["edge"][edgeIdCopy].target = nodeIdCopy;
            }
            // Добавляем дубликат ребра с дополнительным идентификатором
            data["edge_id"].push(edgeIdDuplicate);
            data["edge"][edgeIdDuplicate] = data["edge"][edgeIdCopy];

            // Изменяем идентификаторы для следующих элементов
            nodeIdCopy += "c";
            edgeIdCopy += "c";
            edgeIdDuplicate = edgeIdCopy + "d";
            flag = true;
          }
        });
      }
    });

    // Если оборудование установлено для всех элементов
    if (!flag) {
      if (!data["node_id"].includes(nodeId)) {
        data["node_id"].push(nodeId);
        // Добавляем данные об узле в объект data
        data["node"][nodeId] = {
          system_type: node.data("system_type"),
          node_type: node.data("node_type"),
          price: equipment[0].price,
          cost: equipment[0].cost,
          max_generation: equipment[0].max_gen,
          min_generation: equipment[0].min_gen,
          load: equipment[0].load,
          installed: equipment[0].installed,
          efficiency: equipment[0].efficiency,
        };
      }
    }
  });

  // Добавляем специальные идентификаторы для узла и ребра
  data["node_id"].push("lim");
  data["edge_id"].push("sign", "lim");
  // Выводим данные для отладки
  console.log(data["node_id"]);
  console.log(data["edge_id"]);
  console.log(data);
  // Возвращаем собранные данные о проблеме
  return data;
}

function getNonLinearProblem(visibleElements) {
  // Фильтруем видимые элементы, оставляя только узлы
  var nodes = visibleElements.filter(function (element) {
    return element.isNode();
  });
  // Инициализируем объект для хранения данных о проблеме
  var data = { node_id: [], edge_id: [], node: {}, edge: {} };

  nodes.forEach((node) => {
    let nodeId = node.id();
    let connectedEdges = node.connectedEdges();

    connectedEdges.forEach((edge) => {
      let equipment = edge.data("equipment");
      var sourceNodeId = edge.source().id();
      var targetNodeId = edge.target().id();
      let isSource = function () {
        return nodeId === sourceNodeId;
      };

      let edgeId = edge.id();
      let edgeCopy = edgeId;

      if (isSource()) {
        equipment.forEach((item) => {
          data["edge_id"].push(edgeCopy);

          // Добавляем данные о ребре в объект data
          data["edge"][edgeCopy] = {
            system_type: edge.data("system_type"),
            throughput: item.throughput,
            resistance: item.resistance,
            cost: item.cost,
            length: edge.data("length"),
            source: sourceNodeId,
            target: targetNodeId,
            installed: item.installed,
          };
          edgeCopy += "c";
        });
      }
    });

    if (!data["node_id"].includes(nodeId)) {
      let equipment = node.data("equipment");
      data["node_id"].push(nodeId);
      // Добавляем данные об узле в объект data
      data["node"][nodeId] = {
        system_type: node.data("system_type"),
        node_type: node.data("node_type"),
        price: equipment[0].price,
        cost: equipment[0].cost,
        max_generation: equipment[0].max_gen,
        min_generation: equipment[0].min_gen,
        load: equipment[0].load,
        installed: equipment[0].installed,
        efficiency: equipment[0].efficiency,
      };
    }
  });
  data["node_id"].push("lim");
  data["edge_id"].push("sign", "lim");
  // Выводим данные для отладки
  console.log(data["node_id"]);
  console.log(data["edge_id"]);
  console.log(data);
  // Возвращаем собранные данные о проблеме
  return data;
}

// Функция для получения идентификаторов выбранных чекбоксов
function getSelectedIds(checkboxes) {
  // Создаем пустой массив для хранения идентификаторов выбранных чекбоксов
  let selectedIds = [];

  // Перебираем каждый чекбокс в переданном массиве
  checkboxes.forEach((checkbox) => {
    // Получаем значение текущего чекбокса
    const value = checkbox.value;
    // Добавляем значение в массив selectedIds
    selectedIds.push(value);
  });

  // Возвращаем массив с идентификаторами выбранных чекбоксов
  return selectedIds;
}

// Функция для установки чекбокса с определенным идентификатором в состояние "отмечено"
function setCheckedSystemTypesCheckboxes(type, checkboxes) {
  switch (type) {
    case "power":
      checkboxes[0].checked = true;
      return checkboxes[0];
    case "heat":
      checkboxes[1].checked = true;
      return checkboxes[1];
    case "fuel":
      checkboxes[2].checked = true;
      return checkboxes[2];
    case "cold":
      checkboxes[3].checked = true;
      return checkboxes[3];
    default:
      checkboxes.forEach((checkbox) => {
        checkbox.checked = false;
      });
      return null;
  }
}
