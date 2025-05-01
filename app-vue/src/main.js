import { createApp } from 'vue';
import 'primeicons/primeicons.css';
import './style.css';
import App from './App.vue';

import PrimeVue from 'primevue/config';
import Nora from '@primevue/themes/nora';
import router from "./router/index.js";

import Button from "primevue/button";
import Menubar from 'primevue/menubar';
import Card from 'primevue/card';
import Tabs from 'primevue/tabs';
import TabList from 'primevue/tablist';
import Tab from 'primevue/tab';
import TabPanels from 'primevue/tabpanels';
import TabPanel from 'primevue/tabpanel';
import Select from 'primevue/select';
import InputMask from 'primevue/inputmask';
import InputNumber from 'primevue/inputnumber';
import Textarea from 'primevue/textarea';
import SplitButton from 'primevue/splitbutton';
import Toolbar from 'primevue/toolbar';
import DataTable from 'primevue/datatable';
import Column from 'primevue/column';
import ColumnGroup from 'primevue/columngroup';   // optional
import Row from 'primevue/row';
import Divider from 'primevue/divider';// optional
import Dialog from 'primevue/dialog';
import InputText from "primevue/inputtext";
import AutoComplete from 'primevue/autocomplete';
import Paginator from "primevue/paginator";
import ContextMenu from "primevue/contextmenu";
import Tooltip from "primevue/tooltip";
import Toast from "primevue/toast";
import ToastService from "primevue/toastservice";
import ToggleSwitch from "primevue/toggleswitch";
import InputGroup from "primevue/inputgroup";
import ToggleButton from "primevue/togglebutton";
import ProgressSpinner from "primevue/progressspinner";
import Message from "primevue/message";



const app = createApp(App)
app.use(router);
app.component('Button', Button);
app.component('Menubar', Menubar);
app.component('Card', Card);
app.component('Toolbar', Toolbar);
app.component('Tabs', Tabs);
app.component('TabList', TabList);
app.component('Tab', Tab);
app.component('TabPanels', TabPanels);
app.component('TabPanel', TabPanel);
app.component('Select', Select);
app.component('InputMask', InputMask);
app.component('InputNumber', InputNumber);
app.component('Textarea', Textarea);
app.component('SplitButton', SplitButton);
app.component('DataTable', DataTable);
app.component('Column', Column);
app.component('ColumnGroup', ColumnGroup);
app.component('Row', Row);
app.component('Divider', Divider);
app.component('Dialog', Dialog);
app.component('InputText', InputText);
app.component('AutoComplete', AutoComplete);
app.component('Paginator', Paginator);
app.component('ContextMenu', ContextMenu);
app.component('Tooltip', Tooltip);
app.component('Toast', Toast);
app.component('ToggleSwitch', ToggleSwitch);
app.component('InputGroup', InputGroup);
app.component('ToggleButton', ToggleButton);
app.component('ProgressSpinner', ProgressSpinner);
app.component('Message', Message);
app.use(PrimeVue, {
    theme: {
        preset: Nora
    }
});
app.use(ToastService);
app.directive('tooltip', Tooltip);
app.mount('#app');

