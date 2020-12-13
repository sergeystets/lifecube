import Vue from 'vue'
import App from './App.vue'
import Vuetify from 'vuetify'
import vuetify from './plugins/vuetify';
import 'vuetify/dist/vuetify.min.css'
import 'vue-material-design-icons/styles.css';
import router from './router'
import {VueReCaptcha} from 'vue-recaptcha-v3'

Vue.config.productionTip = false

Vue.use(Vuetify);
Vue.use(VueReCaptcha, {siteKey: 'replace_with_real_site', loaderOptions: {useRecaptchaNet: true}})

let filter = function (text, length) {
  let clamp = '...';
  let node = document.createElement('div');
  node.innerHTML = text;
  let content = node.textContent;
  return content.length > length ? content.slice(0, length) + clamp : content;
};

Vue.filter('truncate', filter);

new Vue({
  router,
  vuetify,
  render: h => h(App),
}).$mount('#app')
