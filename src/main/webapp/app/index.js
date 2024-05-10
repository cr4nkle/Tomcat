const objectsLine = [
  {
    id: 1,
    type: "heat",
    name: "Труба №1",
    throughput: 10,
    resistance: 1,
    cost: 200.0,
  },
  {
    id: 2,
    type: "heat",
    name: "Труба №2",
    throughput: 20,
    resistance: 4,
    cost: 100.0,
  },
  {
    id: 3,
    type: "heat",
    name: "Труба №3",
    throughput: 30,
    resistance: 2,
    cost: 400.0,
  },
];
const objectsSource = [
  {
    max_gen: 10,
    min_gen: 10,
    id: 1,
    type: "heat",
    name: "ТЭЦ",
    price: 0.0,
    cost: 100000.0,
    efficiency: 0.95,
  },
  {
    max_gen: 60,
    min_gen: 10,
    id: 2,
    type: "heat",
    name: "ТЭЦ",
    price: 0.0,
    cost: 200000.0,
    efficiency: 0.95,
  },
  {
    max_gen: 50,
    min_gen: 10,
    id: 3,
    type: "heat",
    name: "ТЭЦ",
    price: 0.0,
    cost: 300000.0,
    efficiency: 0.95,
  },
];
const objectsConsumer = [
  {
    id: 1,
    type: "heat",
    name: "Дом №1",
    load: 10,
  },
  {
    id: 2,
    type: "heat",
    name: "Дом №2",
    load: 20,
  },
  {
    id: 3,
    type: "heat",
    name: "Дом №3",
    load: 30,
  },
];

let selectedObjectsLineIds = [];
let selectedObjectsSourceIds = [];
let selectedConsumerIds = [];
let currentArrayType = null;

// Функция для получения элемента по его id
const getElement = (id) => document.getElementById(id);

// Объект для хранения элементов
const elements = {
  startModalTitle: getElement("startModalTitle"),
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
  poverNodeModalCheckboxText: getElement("poverNodeModalCheckboxText"),
  fuelNodeModalCheckboxText: getElement("fuelNodeModalCheckboxText"),
  heatNodeModalCheckboxText: getElement("heatNodeModalCheckboxText"),
  blockNodeModalCheckboxText: getElement("blockNodeModalCheckboxText"),
  selectAllNodeModalCheckboxText: getElement("selectAllNodeModalCheckboxText"),
  cytoscapeContainer: getElement("cy"),
};

const localeUrl = "http://localhost:8080/app/api/get/locale";
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
    elements.startModalTitle.textContent = data.startModal.title;
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
    elements.poverNodeModalCheckboxText.textContent =
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

// Добавление обработчиков событий
addEventListeners();

//вывод окна с сообщением
const showMessage = (text) => {
  elements.modalText.textContent = text;
  elements.messageModal.showModal();
  elements.messageModalCloseBtn.addEventListener("click", () => {
    elements.messageModal.close();
  });
};

// Добавление обработчиков событий
function addEventListeners() {
  const layout = { name: "preset" };
  var cy = null;

  elements.openNewModelModalToolbarBtn.addEventListener("click", () => {
    elements.newModelModal.showModal();
  });

  elements.openChooseModelModalToolbarBtn.addEventListener("click", () => {
    elements.chooseModelModal.showModal();
  });

  elements.openModelBtn.addEventListener("click", () => {
    if (selectedModel) {
      showMessage(`Выбранная модель: ${selectedModel.textContent}`);
      elements.chooseModelModal.close();
    } else {
      showMessage("Модель не выбрана");
    }
  });

  //на стартовом экране нажимаем создать новую
  elements.newModelBtn.addEventListener("click", () => {
    elements.startModal.close(); //закрываем стаьровое окно
    elements.newModelModal.showModal(); //и открываем ввод названия модели
  });

  //нажатие кнопки создать модель
  elements.createModelBtn.addEventListener("click", async () => {
    elements.newModelModal.close();

    const modelName = elements.newModelNameInput.value;
    let messageText = "Вы ничего не ввели!!!";

    if (modelName) {
      // Если пользователь ввел название, выводим его в диалоговом окне
      messageText = `Вы ввели: ${modelName}`;
      showMessage(messageText);
      // Инициализируем пустой граф и применяем к нему методы
      cy = await initializeEmptyGraph(elements.cytoscapeContainer, layout);
      initializeGraphMethods(cy);
    } else {
      // Если пользователь отменил ввод, выводим сообщение
      showMessage(messageText);
      elements.newModelModal.close();
      elements.newModelNameInput.value = "";
      elements.newModelModal.showModal();
    }
    elements.newModelNameInput.value = "";
  });

  elements.openChooseModelModalBtn.addEventListener("click", () => {
    elements.startModal.close();
    elements.chooseModelModal.showModal();
  });

  elements.modelList.addEventListener("click", (event) => {
    const listItem = event.target.closest("li");
    if (!listItem) return;

    selectModel(listItem);
  });

  // Операции

  const operationButtons = [
    elements.addEdgeToolbarBtn,
    elements.addNodeToolbarBtn,
    elements.editToolbarBtn,
    elements.deleteItemToolbarBtn,
    elements.calculateToolbarBtn,
    elements.moveToolbarBtn,
  ];
  function toggleButtons(enable, buttons) {
    removeSelectedClass(buttons);
    buttons.forEach((button) => {
      button.disabled = !enable;
    });
  }
  function toggleSelected(isSelected, element) {
    removeSelectedClass([...operationButtons, ...elementButtons]);
    if (isSelected) {
      element.classList.remove("selected");
    } else {
      element.classList.add("selected");
    }
  }
  elements.addEdgeToolbarBtn.addEventListener("click", () => {
    const isSelected =
      elements.addEdgeToolbarBtn.classList.contains("selected");

    toggleSelected(isSelected, elements.addEdgeToolbarBtn);
    toggleButtons(!isSelected, elementButtons);
  });

  elements.addNodeToolbarBtn.addEventListener("click", () => {
    const isSelected =
      elements.addNodeToolbarBtn.classList.contains("selected");
    toggleButtons(false, elementButtons);
    toggleSelected(isSelected, elements.addNodeToolbarBtn);
  });

  elements.editToolbarBtn.addEventListener("click", () => {
    const isSelected = elements.editToolbarBtn.classList.contains("selected");
    toggleButtons(false, elementButtons);
    toggleSelected(isSelected, elements.editToolbarBtn);
  });

  elements.deleteItemToolbarBtn.addEventListener("click", () => {
    const isSelected =
      elements.deleteItemToolbarBtn.classList.contains("selected");
    toggleButtons(false, elementButtons);
    toggleSelected(isSelected, elements.deleteItemToolbarBtn);
  });

  elements.moveToolbarBtn.addEventListener("click", () => {
    const isSelected = elements.moveToolbarBtn.classList.contains("selected");
    toggleButtons(false, elementButtons);
    toggleSelected(isSelected, elements.moveToolbarBtn);
  });

  //Операция расчет

  elements.calculateToolbarBtn.addEventListener("click", () => {
    elements.resultModalText.textContent = "ОТЧИСЛЕН!!!";
    elements.resultModal.showModal();
  });

  elements.resultModalCloseBtn.addEventListener("click", () => {
    elements.resultModal.close();
  });

  elements.resultModalSaveBtn.addEventListener("click", () => {
    elements.resultModal.close();
  });

  //Элементы
  const elementButtons = [
    elements.sourceToolbarBtn,
    elements.distributionNodeToolbarBtn,
    elements.consumerToolbarBtn,
  ];
  elements.sourceToolbarBtn.addEventListener("click", () => {
    if (elements.addEdgeToolbarBtn.classList.contains("selected")) {
      removeSelectedClass(elementButtons);
      elements.sourceToolbarBtn.classList.add("selected");
    }
  });

  elements.distributionNodeToolbarBtn.addEventListener("click", () => {
    if (elements.addEdgeToolbarBtn.classList.contains("selected")) {
      removeSelectedClass(elementButtons);
      elements.distributionNodeToolbarBtn.classList.add("selected");
    }
  });

  elements.consumerToolbarBtn.addEventListener("click", () => {
    if (elements.addEdgeToolbarBtn.classList.contains("selected")) {
      removeSelectedClass(elementButtons);
      elements.consumerToolbarBtn.classList.add("selected");
    }
  });

  elements.nodeModalCloseBtn.addEventListener("click", () => {
    elements.nodeModal.close();
    elements.selectAllNodeModalCheckbox.checked = false;

    selectedObjectsLineIds = [];
    selectedObjectsSourceIds = [];
    selectedConsumerIds = [];
    currentArrayType = null;
  });

  elements.nodeModalSaveBtn.addEventListener("click", () => {
    if (currentArrayType === "line") alert(selectedObjectsLineIds);
    if (currentArrayType === "source") alert(selectedObjectsSourceIds);
    if (currentArrayType === "consumer") alert(selectedConsumerIds);

    elements.nodeModal.close();
    elements.selectAllNodeModalCheckbox.checked = false;

    selectedObjectsLineIds = [];
    selectedObjectsSourceIds = [];
    selectedConsumerIds = [];
    currentArrayType = null;
  });

  elements.lineBtn.addEventListener("click", () => {
    currentArrayType = "line";
    elements.nodeModalList.innerHTML = "";
    objectsLine.forEach((obj) => {
      const nodeElement = createNodeElementLineItem(obj);
      nodeModalList.appendChild(nodeElement);
    });
    elements.nodeModal.showModal();
  });

  elements.sourceBtn.addEventListener("click", () => {
    currentArrayType = "source";
    elements.nodeModalList.innerHTML = "";
    objectsSource.forEach((obj) => {
      const nodeElement = createNodeElementSourceItem(obj);
      nodeModalList.appendChild(nodeElement);
    });
    elements.nodeModal.showModal();
  });

  elements.consumerBtn.addEventListener("click", () => {
    currentArrayType = "consumer";
    elements.nodeModalList.innerHTML = "";
    objectsConsumer.forEach((obj) => {
      const nodeElement = createNodeElementConsumerItem(obj);
      nodeModalList.appendChild(nodeElement);
    });
    elements.nodeModal.showModal();
  });

  elements.blockNodeModalCheckbox.addEventListener("change", function () {
    const checkboxes = document.querySelectorAll(
      '.modal_node_item input[type="checkbox"]'
    );

    checkboxes.forEach((checkbox) => {
      checkbox.disabled = this.checked;
    });
  });

  elements.selectAllNodeModalCheckbox.addEventListener("change", function () {
    const checkboxes = document.querySelectorAll(
      '.modal_node_item input[type="checkbox"]'
    );

    selectedObjectsLineIds = [];
    selectedObjectsSourceIds = [];
    selectedConsumerIds = [];

    checkboxes.forEach((checkbox) => {
      checkbox.checked = this.checked;

      if (currentArrayType === "line")
        selectedObjectsLineIds.push(checkbox.value);
      if (currentArrayType === "source")
        selectedObjectsSourceIds.push(checkbox.value);
      if (currentArrayType === "consumer")
        selectedConsumerIds.push(checkbox.value);
    });

    if (!this.checked) {
      selectedObjectsLineIds = [];
      selectedObjectsSourceIds = [];
      selectedConsumerIds = [];
    }
  });
}

//список моделей
const modelList = [
  { id: "1", name: "Graph1" },
  { id: "2", name: "Graph2" },
  { id: "3", name: "Graph3" },
  { id: "4", name: "Graph4" },
  { id: "5", name: "Graph5" },
  { id: "6", name: "Graph6" },
  { id: "7", name: "Graph7" },
  { id: "8", name: "Graph8" },
  { id: "9", name: "Graph9" },
  { id: "10", name: "Graph10" },
];

let selectedModel = null;

function selectModel(listItem) {
  if (selectedModel) {
    selectedModel.classList.remove("selected");
  }
  listItem.classList.add("selected");
  selectedModel = listItem;
}

modelList.forEach((item) => {
  const listItemElement = document.createElement("li");
  listItemElement.className = "modal_choose_model_item";
  listItemElement.textContent = item.name;
  elements.modelList.appendChild(listItemElement);
});

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

  const checkbox = element.querySelector('input[type="checkbox"]');

  checkbox.addEventListener("change", function () {
    if (this.checked) {
      selectedObjectsLineIds.push(this.value);
    } else {
      selectedObjectsLineIds = selectedObjectsLineIds.filter(
        (id) => id !== this.value
      );
    }
  });
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

  const checkbox = element.querySelector('input[type="checkbox"]');

  checkbox.addEventListener("change", function () {
    if (this.checked) {
      selectedObjectsSourceIds.push(this.value);
    } else {
      selectedObjectsSourceIds = selectedObjectsLineIds.filter(
        (id) => id !== this.value
      );
    }
  });
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

  const checkbox = element.querySelector('input[type="checkbox"]');

  checkbox.addEventListener("change", function () {
    if (this.checked) {
      selectedConsumerIds.push(this.value);
    } else {
      selectedConsumerIds = selectedConsumerIds.filter(
        (id) => id !== this.value
      );
    }
  });
  return element;
}

//код нудный для визуализации
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
const editCheckboxes1 = document.querySelectorAll(".group2");
const editCheckboxes2 = document.querySelectorAll(".group3");
const editCheckboxes3 = document.querySelectorAll(".group4");
const saveEquipment = document.getElementById("save-equipment");
//эндпоинты
const styleUrl = "http://localhost:8080/app/api/get/style";
const saveModelUrl = "http://localhost:8080/app/api/post/saveModel";
const calculateUrl = "http://localhost:8080/app/api/post/calculate";
//переменные с начальным значением
var addEdgeOn,
  addNodeOn,
  editModeOn,
  delModeOn = false;
var nodeType;
var nodeId = 1;
var edgeId;
var clickCount = 0;
var toolIsSelected = false;
var firstNodeSelected,
  secondNodeSelected,
  selectedNode,
  selectedEdge = null;
var modelName;
var linesData = {};
var sourcesData = {};
var consumersData = {};
// Переменные, необходимые для визуализации
const container = document.getElementById("cy");
const layout = { name: "preset" };
var cy = null;
var canvas = null;

// Обработчик события клика для кнопки создания новой модели
create.addEventListener("click", async () => {
  // Запрашиваем у пользователя название модели через диалоговое окно
  modelName = prompt("Введите название модели:", "Модель");
  if (modelName) {
    // Если пользователь ввел название, выводим его в диалоговом окне
    alert("Вы ввели: " + modelName);
    // Инициализируем пустой граф и применяем к нему методы
    cy = await initializeEmptyGraph(container);
    initializeGraphMethods(cy);
  } else {
    // Если пользователь отменил ввод, выводим сообщение
    alert("Ввод отменен");
  }
});

// Обработчик события изменения для кнопки загрузки файла с ПК
uploadFromPc.addEventListener("change", async function (e) {
  // Получаем выбранный файл
  var file = e.target.files[0];
  // Устанавливаем имя модели равным имени файла
  modelName = file.name;
  // Создаем URL для доступа к файлу
  var url = URL.createObjectURL(file);
  // Инициализируем граф с данными из файла
  cy = await initializeGraph(new Request(url), container);
  // Применяем к графу методы
  initializeGraphMethods(cy);
  // Удаляем расширение из имени файла
  modelName = removeExtension(modelName);
  // Выводим сообщение с именем загруженной модели
  alert("Вы открыли модель с названием " + modelName);
});

// Обработчик события клика для кнопки загрузки модели из базы данных
uploadFromDb.addEventListener("click", async () => {
  // Запрашиваем у пользователя название модели через диалоговое окно
  modelName = prompt("Введите название модели:", "Модель");
  if (modelName) {
    // Если пользователь ввел название, выводим его в диалоговом окне
    alert("Вы ввели: " + modelName);
  } else {
    // Если пользователь отменил ввод, выводим сообщение
    alert("Ввод отменен");
  }
  // Создаем URL для запроса к серверу с именем модели
  let url = `http://localhost:8080/app/api/get/model?name=${modelName}`;
  // Инициализируем граф с данными из сервера
  cy = await initializeGraph(new Request(url), container);
  // Применяем к графу методы
  initializeGraphMethods(cy);
});

// Обработчик события клика для кнопки сохранения модели в базе данных
saveToDb.addEventListener("click", async () => {
  if (cy !== null) {
    // Получаем JSON представление графа
    var graphJSON = cy.json();
    // Получаем идентификаторы всех ребер
    var edgeIds = cy.elements("edge").map(function (edge) {
      return edge.id();
    });
    // Получаем идентификаторы всех узлов
    var nodeIds = cy.elements("node").map(function (node) {
      return node.id();
    });
    // Устанавливаем имя модели без расширения
    graphJSON.name = removeExtension(modelName);
    // Добавляем идентификаторы узлов и ребер в JSON
    graphJSON["node_ids"] = nodeIds;
    graphJSON["edge_ids"] = edgeIds;
    // Преобразуем JSON в строку
    let data = JSON.stringify(graphJSON);
    // Отправляем данные на сервер для сохранения модели
    let result = await loadDataOnServer(saveModelUrl, data, "POST");
  }
});

// Обработчик события клика для кнопки скачивания модели
download.addEventListener("click", () => {
  // Вызываем функцию для скачивания графа
  downloadGraph(modelName);
});

// Обработчик события клика для кнопки удаления модели
deleteModel.addEventListener("click", () => {
  // Создаем URL для запроса к серверу на удаление модели
  let deleteUrl = `http://localhost:8080/app/api/delete/model?name=${modelName}`;
  // Вызываем функцию для удаления данных с сервера
  deleteDataFromServer(deleteUrl);
});

// Обработчик события клика для каждой кнопки инструментов
toolBtns.forEach((btn) => {
  btn.addEventListener("click", () => {
    // Устанавливаем активное состояние для выбранной кнопки
    setActiveToBtn(document.querySelector(".active"), btn);
    // Вызываем функцию для обработки выбора инструмента
    btnSelected(btn.id);
    // Устанавливаем флаг, что инструмент выбран
    toolIsSelected = true;
    // Выводим идентификатор выбранной кнопки в консоль
    console.log(btn.id);
  });
});

// Обработчик события клика для кнопки "Рассчитать"
calculate.addEventListener("click", async () => {
  // Проверяем, что граф инициализирован
  if (cy !== null) {
    // Получаем все видимые элементы графа (узлы и ребра)
    var visibleElements = cy.elements().filter(function (element) {
      return element.style("display") !== "none";
    });

    // Получаем данные проблемы для расчета
    let problem = getLinearProblem(visibleElements);
    let nProblem = getNonLinearProblem(visibleElements);

    // Преобразуем данные проблемы в JSON строку
    let data = JSON.stringify(problem);
    // Отправляем данные на сервер для расчета
    let result = await loadDataOnServer(calculateUrl, data, "POST");
    // Выводим результат расчета в консоль
    console.log(result);
  }
});

// Обработчик события клика для кнопки "Переместить"
move.addEventListener("click", () => {
  // Проверяем, что граф инициализирован
  if (cy !== null) {
    // Получаем группы чекбоксов для различных типов элементов
    const checkboxesGroup6 = document.querySelectorAll(".group6");
    const checkboxesGroup5 = document.querySelectorAll(".group5");
    const checkboxesGroup7 = document.querySelectorAll(".group7");

    // Сбрасываем состояние чекбоксов
    setChecked("", editCheckboxes1);
    setChecked("", editCheckboxes2);
    setChecked("", editCheckboxes3);
    setChecked("", checkboxesGroup5);
    setChecked("", checkboxesGroup6);
    setChecked("", checkboxesGroup7);

    // Сбрасываем режимы работы
    setModeValue(false, false, false, false);

    // Сбрасываем выбранные элементы
    selectedNode = null;
    selectedEdge = null;
  }
});

// Обработчик события клика по кнопке сохранения оборудования
saveEquipment.addEventListener("click", () => {
  // Проверяем, что граф инициализирован
  if (cy !== null) {
    // Получаем группы чекбоксов для различных типов оборудования
    const checkboxesGroup6 = document.querySelectorAll(".group6");
    const checkboxesGroup5 = document.querySelectorAll(".group5");
    const checkboxesGroup7 = document.querySelectorAll(".group7");

    // Проверяем, что выбран узел или ребро
    if (selectedNode !== null) {
      // Получаем тип выбранного узла
      var type = selectedNode.data("node_type");

      // Обработка для узла-источника
      if (type === "source") {
        // Фильтруем выбранные чекбоксы
        const selectedCheckboxes = Array.from(checkboxesGroup6).filter(
          (checkbox) => checkbox.checked
        );
        // Получаем идентификаторы выбранных чекбоксов
        var selectedIds = getSelectedIds(selectedCheckboxes);
        // Получаем данные оборудования по идентификаторам
        const selectedData = selectedIds.map((id) => {
          return sourcesData[id];
        });
        // Получаем текущие данные узла
        var nodeData = selectedNode.data();

        // Очищаем текущие данные оборудования узла
        nodeData.equipment.length = 0;

        // Добавляем выбранные данные оборудования в узел
        selectedData.forEach((data) => {
          nodeData.equipment.push(data);
        });

        // Обновляем данные узла в графе
        selectedNode.data(nodeData);
      }
      // Обработка для узла-потребителя
      else if (type === "consumer") {
        // Фильтруем выбранные чекбоксы
        const selectedCheckboxes = Array.from(checkboxesGroup5).filter(
          (checkbox) => checkbox.checked
        );
        // Получаем идентификаторы выбранных чекбоксов
        var selectedIds = getSelectedIds(selectedCheckboxes);
        // Получаем данные оборудования по идентификаторам
        const selectedData = selectedIds.map((id) => {
          return consumersData[id];
        });
        // Получаем текущие данные узла
        var nodeData = selectedNode.data();

        // Очищаем текущие данные оборудования узла
        nodeData.equipment.length = 0;

        // Добавляем выбранные данные оборудования в узел
        selectedData.forEach((data) => {
          nodeData.equipment.push(data);
        });

        // Обновляем данные узла в графе
        selectedNode.data(nodeData);
      }
    }
    // Обработка для выбранного ребра
    else if (selectedEdge !== null) {
      // Фильтруем выбранные чекбоксы
      const selectedCheckboxes = Array.from(checkboxesGroup7).filter(
        (checkbox) => checkbox.checked
      );
      // Получаем идентификаторы выбранных чекбоксов
      var selectedIds = getSelectedIds(selectedCheckboxes);
      // Получаем данные оборудования по идентификаторам
      const selectedData = selectedIds.map((id) => {
        return linesData[id];
      });
      // Получаем текущие данные ребра
      var edgeData = selectedEdge.data();

      // Очищаем текущие данные оборудования ребра
      edgeData.equipment.length = 0;

      // Добавляем выбранные данные оборудования в ребро
      selectedData.forEach((data) => {
        edgeData.equipment.push(data);
      });

      // Обновляем данные ребра в графе
      selectedEdge.data(edgeData);
    }
  }
});

// Перебираем каждый чекбокс в массиве hideCheckboxes
hideCheckboxes.forEach((checkbox) => {
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
        }
      } else {
        // Если чекбокс отмечен, показываем все скрытые элементы
        cy.elements(":hidden").show();
      }
    }
  });
});
//функции для работы приложения
function removeExtension(filename) {
  return filename.substring(0, filename.lastIndexOf(".")) || filename;
}
// Функция для установки режимов работы с графиком
function setModeValue(isAddNodeOn, isAddEdgeOn, isEditModeOn, isDelModeOn) {
  // Устанавливаем флаги для режимов добавления узла, добавления ребра, редактирования и удаления
  addNodeOn = isAddNodeOn;
  addEdgeOn = isAddEdgeOn;
  editModeOn = isEditModeOn;
  delModeOn = isDelModeOn;
}

// Функция для установки активного состояния кнопки
function setActiveToBtn(activeOption, btn) {
  // Если предыдущая активная опция была выбрана, снимаем с неё класс "active"
  if (activeOption != null) {
    activeOption.classList.remove("active");
  }
  // Добавляем класс "active" к выбранной кнопке
  btn.classList.add("active");
}

// Функция для обработки выбора типа узла или режима работы
function btnSelected(btnId) {
  // Определяем действие в зависимости от идентификатора выбранной кнопки
  switch (btnId) {
    case "triangle":
      // Устанавливаем тип узла как источник
      nodeType = "source";
      break;
    case "rectangle":
      // Устанавливаем тип узла как коннектор
      nodeType = "connector";
      break;
    case "circle":
      // Устанавливаем тип узла как потребитель
      nodeType = "consumer";
      break;
    case "edit":
      // Включаем режим редактирования
      setModeValue(false, false, true, false);
      // Сбрасываем выбранные элементы
      selectedNode = null;
      selectedEdge = null;
      break;
    case "add-node":
      // Включаем режим добавления узла
      setModeValue(true, false, false, false);
      // Сбрасываем выбранные элементы
      selectedNode = null;
      selectedEdge = null;
      break;
    case "add-edge":
      // Включаем режим добавления ребра
      setModeValue(false, true, false, false);
      // Сбрасываем выбранные элементы
      selectedNode = null;
      selectedEdge = null;
      break;
    case "delete-element":
      // Включаем режим удаления элемента
      setModeValue(false, false, false, true);
      // Сбрасываем выбранные элементы
      selectedNode = null;
      selectedEdge = null;
      break;
    default:
      // Если нет соответствия, устанавливаем тип узла по умолчанию и сбрасываем режимы работы
      nodeType = "circle";
      setModeValue(false, false, false, false);
      // Сбрасываем выбранные элементы
      selectedNode = null;
      selectedEdge = null;
  }
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

// Функция для скачивания графа в формате JSON
function downloadGraph(fileName) {
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
    link.download = fileName;
    // Инициируем скачивание
    link.click();
  } else {
    alert("Модель не создана");
  }
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
function initializeGraph(request, container) {
  // Возвращаем промис, который будет разрешен с объектом графа Cytoscape
  return new Promise((resolve, reject) => {
    // Используем Promise.all для параллельного выполнения двух асинхронных запросов
    Promise.all([
      // Первый запрос: получаем данные графа из запроса
      fetch(request).then((res) => {
        return res.json();
      }),
      // Второй запрос: загружаем стили графа с сервера
      loadDataFromServer(styleUrl).then((res) => {
        return res;
      }),
    ])
      .then((dataArray) => {
        // После успешного выполнения обоих запросов создаем объект графа Cytoscape
        console.log(dataArray);
        const cy = cytoscape({
          container: container, // Контейнер для графа
          style: dataArray[1].style, // Стили графа
          elements: dataArray[0].elements, // Элементы графа
          layout: layout, // Конфигурация расположения элементов
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
function initializeEmptyGraph(container) {
  // Возвращаем промис, который будет разрешен с объектом графа Cytoscape
  return new Promise((resolve, reject) => {
    // Используем Promise.all для выполнения асинхронного запроса загрузки стилей графа
    Promise.all([
      loadDataFromServer(styleUrl).then((res) => {
        return res;
      }),
    ])
      .then((dataArray) => {
        // Создаем объект графа Cytoscape без элементов
        const cy = cytoscape({
          container: container, // Контейнер для графа
          style: dataArray[0].style, // Стили графа
          elements: [], // Пустой массив элементов
          layout: layout, // Конфигурация расположения элементов
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

// Функция для инициализации методов работы с графом в Cytoscape
function initializeGraphMethods(cy) {
  // Устанавливаем начальный масштаб и позицию камеры
  cy.zoom(1.25);
  cy.pan({ x: 270, y: 450 });
  // Получаем максимальный идентификатор узла в графе
  nodeId = getMaxNodeId(cy);
  // Устанавливаем начальные значения режимов работы
  setModeValue(false, false, false, false);
  // Получаем уникальные типы систем в графе и устанавливаем чекбоксы в соответствии с этими типами
  var ids = findSystemTypes(cy);
  setCheckedByIds(ids, hideCheckboxes);

  // Обработчик события клика по узлу
  cy.on("click", "node", async function (event) {
    console.log("node click");
    var node = event.target;
    var id = node.data("id");
    var equipment = node.data("equipment");
    var systemType = node.data("system_type");
    var nodeType = node.data("node_type");
    addEdgeToGraph(id);

    if (editModeOn) {
      selectedNode = node;
      selectedEdge = null;
      resetCheckboxes();
      await handleNodeClick(nodeType, systemType, equipment);
    } else if (delModeOn) {
      node.remove();
      nodeId = getMaxNodeId(cy);
    }
  });

  // Обработчик события клика по ребру
  cy.on("click", "edge", async function (event) {
    console.log("edge click");
    var edge = event.target;
    var equipment = edge.data("equipment");
    var systemType = edge.data("system_type");

    if (editModeOn) {
      selectedNode = null;
      selectedEdge = edge;
      await handleEdgeClick(systemType, equipment);
    } else if (delModeOn) {
      edge.remove();
    }
  });

  // Обработчик события клика по области графа
  cy.on("click", function (event) {
    if (event && event.position && addNodeOn) {
      var pos = event.position;
      addNodeToGraph(pos);
      toolIsSelected = false;
    }
  });
}

// Функция для сброса состояния чекбоксов
function resetCheckboxes() {
  const checkboxesGroup5 = document.querySelectorAll(".group5");
  const checkboxesGroup6 = document.querySelectorAll(".group6");
  const checkboxesGroup7 = document.querySelectorAll(".group7");
  setChecked("", checkboxesGroup5);
  setChecked("", checkboxesGroup6);
  setChecked("", checkboxesGroup7);
}

// Функция для обработки клика по узлу
async function handleNodeClick(nodeType, systemType, equipment) {
  let url;
  if (nodeType === "source") {
    url = `http://localhost:8080/app/api/get/sources?type=${systemType}`;
    const checkedCheckboxes = await loadAndDisplaySourcesData(url);
    updateCheckboxesState(equipment, checkedCheckboxes);
    setChecked(systemType, editCheckboxes2);
  } else if (nodeType === "consumer") {
    url = `http://localhost:8080/app/api/get/consumers?type=${systemType}`;
    const checkedCheckboxes = await loadAndDisplayConsumersData(url);
    updateCheckboxesState(equipment, checkedCheckboxes);
    setChecked(systemType, editCheckboxes1);
  }
}

// Функция для обработки клика по ребру
async function handleEdgeClick(systemType, equipment) {
  let url = `http://localhost:8080/app/api/get/lines?type=${systemType}`;
  const checkedCheckboxes = await loadAndDisplayLinesData(url);
  updateCheckboxesState(equipment, checkedCheckboxes);
  setChecked(systemType, editCheckboxes3);
}

// Функция для обновления состояния чекбоксов
function updateCheckboxesState(equipment, checkboxes) {
  equipment.forEach((element) => {
    const checkbox = Array.from(checkboxes).find(
      (cb) => cb.id === `${element.id}`
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
    // Получаем уникальные типы систем в графе и устанавливаем чекбоксы в соответствии с этими типами
    var ids = findSystemTypes(cy);
    setCheckedByIds(ids, hideCheckboxes);
    // Создаем новый узел с уникальным идентификатором, типом узла, типом системы и начальными данными оборудования
    var node = [
      {
        data: {
          id: `n${nodeId++}`,
          node_type: nodeType,
          system_type: "heat",
          grouped: "false",
          group_name: "",
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
  }
}

// Функция для добавления ребра в граф
function addEdgeToGraph(id) {
  // Проверяем, разрешено ли добавление ребра
  if (addEdgeOn) {
    // Получаем уникальные типы систем в графе и устанавливаем чекбоксы в соответствии с этими типами
    var ids = findSystemTypes(cy);
    setCheckedByIds(ids, hideCheckboxes);
    // Проверяем, какой шаг добавления ребра (первый или второй узел выбран)
    if (clickCount == 0) {
      // Если это первый выбранный узел, сохраняем его идентификатор
      firstNodeSelected = id;
      clickCount++;
    } else if (clickCount == 1) {
      // Если это второй выбранный узел, создаем ребро между первым и вторым узлами
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
          console.log(item);
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

//конец

//как-то оптимизировать этот код
async function loadAndDisplayConsumersData(url) {
  const scrollableList = document.getElementById("scrollableList1");
  // Очищаем список перед загрузкой новых данных
  scrollableList.innerHTML = "";

  // Загружаем данные с сервера
  const data = await loadDataFromServer(url);

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
    const checkboxInstalled = document.createElement("input");
    checkboxInstalled.type = "checkbox";
    checkboxInstalled.name = "itemCheckboxInstalled";
    checkboxInstalled.className = "group10";
    checkboxInstalled.value = item.id;
    checkboxInstalled.id = `${item.id}`;

    listItem.appendChild(checkbox);
    listItem.appendChild(document.createTextNode("select"));
    listItem.appendChild(document.createElement("br"));
    listItem.appendChild(checkboxInstalled);
    listItem.appendChild(document.createTextNode("installed"));
    listItem.appendChild(document.createElement("br"));

    const nameText = document.createTextNode(`Наименование: ${item.name}`);
    listItem.appendChild(nameText);
    listItem.appendChild(document.createElement("br"));

    const loadText = document.createTextNode(`Потребление: ${item.load}`);
    listItem.appendChild(loadText);

    scrollableList.appendChild(listItem);

    consumersData[item.id] = {
      id: item.id,
      name: item.name,
      load: item.load,
      installed: false,
    };
  });

  return document.querySelectorAll(".group5");
}

async function loadAndDisplaySourcesData(url) {
  const scrollableList = document.getElementById("scrollableList2");

  // Очищаем список перед загрузкой новых данных
  scrollableList.innerHTML = "";

  // Загружаем данные с сервера
  const data = await loadDataFromServer(url);

  // Создаем и добавляем элементы в DOM
  data.forEach((item) => {
    const listItem = document.createElement("div");
    listItem.style.fontSize = "10px";

    const checkbox = document.createElement("input");
    checkbox.type = "checkbox";
    checkbox.name = "itemCheckbox";
    checkbox.className = "group6";
    checkbox.value = item.id;
    checkbox.id = `${item.id}`;

    const checkboxInstalled = document.createElement("input");
    checkboxInstalled.type = "checkbox";
    checkboxInstalled.name = "itemCheckboxInstalled";
    checkboxInstalled.className = "group9";
    checkboxInstalled.value = item.id;
    checkboxInstalled.id = `${item.id}`;

    listItem.appendChild(checkbox);
    listItem.appendChild(document.createTextNode("select"));
    listItem.appendChild(document.createElement("br"));
    listItem.appendChild(checkboxInstalled);
    listItem.appendChild(document.createTextNode("installed"));
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
    listItem.appendChild(document.createElement("br"));

    const efficiencyText = document.createTextNode(`КПД: ${item.efficiency}`);
    listItem.appendChild(efficiencyText);

    scrollableList.appendChild(listItem);

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

  // Возвращаем все чекбоксы после их создания и добавления в DOM
  return document.querySelectorAll(".group6");
}

async function loadAndDisplayLinesData(url) {
  const scrollableList = document.getElementById("scrollableList3");
  // Очищаем список перед загрузкой новых данных
  scrollableList.innerHTML = "";

  // Загружаем данные с сервера
  const data = await loadDataFromServer(url);
  data.forEach((item) => {
    const listItem = document.createElement("div");
    listItem.style.fontSize = "10px";

    const checkbox = document.createElement("input");
    checkbox.type = "checkbox";
    checkbox.name = "itemCheckbox";
    checkbox.className = "group7";
    checkbox.value = item.id;
    checkbox.id = `${item.id}`;

    const checkboxInstalled = document.createElement("input");
    checkboxInstalled.type = "checkbox";
    checkboxInstalled.name = "itemCheckboxInstalled";
    checkboxInstalled.className = "group8";
    checkboxInstalled.value = item.id;
    checkboxInstalled.id = `${item.id}`;

    listItem.appendChild(checkbox);
    listItem.appendChild(document.createTextNode("select"));
    listItem.appendChild(document.createElement("br"));
    listItem.appendChild(checkboxInstalled);
    listItem.appendChild(document.createTextNode("installed"));
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

    linesData[item.id] = {
      id: item.id,
      name: item.name,
      throughput: item.throughput,
      resistance: item.resistance,
      cost: item.cost,
      installed: false,
    };
  });

  return document.querySelectorAll(".group7");
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
function setChecked(id, checkboxes) {
  // Перебираем каждый чекбокс в переданном массиве
  checkboxes.forEach((checkbox) => {
    // Проверяем, совпадает ли идентификатор текущего чекбокса с заданным
    if (checkbox.id === id) {
      // Если совпадает, выводим идентификатор в консоль
      console.log(checkbox.id);
      // Устанавливаем чекбокс в состояние "отмечено"
      checkbox.checked = true;
    } else {
      // Если не совпадает, устанавливаем чекбокс в состояние "не отмечено"
      checkbox.checked = false;
    }
  });
}
