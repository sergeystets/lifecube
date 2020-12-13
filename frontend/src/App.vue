<template>

  <v-app>
    <v-main>
      <router-view/>
    </v-main>
    <v-footer class="pa-3">
      <v-col
          class="text-center"
          cols="12"
      >
      </v-col>
    </v-footer>
  </v-app>

</template>

<script>

export default {
  name: 'App',
  data() {
    return {}
  },
  mounted() {
  }
}
</script>