<template id="home">

  <v-main>
    <section>
      <v-parallax src="../assets/home-page-bg.jpeg" height="600">
        <v-layout column align-center justify-center class="white--text">
          <h1 class="white--text mb-2 di 1 text-center">Life Cube</h1>
          <div class="subheading mb-4 text-center">Ideas have its home...</div>
          <v-btn class="mt-12" color="blue lighten-2" dark large to="/dashboard">
            Get started
          </v-btn>
        </v-layout>
      </v-parallax>
    </section>

    <section>
      <v-layout
          column
          wrap
          class="my-12"
          align-center
      >
        <v-flex xs12 sm4 class="my-4">
          <div class="text-center">
            <h2 class="headline">Life Cube is a tool for managing your ideas. </h2>
            <span class="subheading">
                It is easy & fun
              </span>
          </div>
        </v-flex>
        <v-flex xs12>
          <v-container grid-list-xl>
            <v-layout row wrap align-center>
              <v-flex xs12 md4>
                <v-card flat class="transparent">
                  <v-card-text class="text-center">
                    <v-icon x-large class="blue--text text--lighten-2">search</v-icon>
                  </v-card-text>
                  <v-card-title primary-title class="layout justify-center">
                    <div class="headline text-center">Search for ideas</div>
                  </v-card-title>
                  <v-card-text>
                    Look for what other people submitted.
                    Get involved and find what you are interested in very quickly. Just start typing
                    and we will find the best match...
                  </v-card-text>
                </v-card>
              </v-flex>
              <v-flex xs12 md4>
                <v-card flat class="transparent">
                  <v-card-text class="text-center">
                    <v-icon x-large class="blue--text text--lighten-2">mdi-lightbulb-on-outline
                    </v-icon>
                  </v-card-text>
                  <v-card-title primary-title class="layout justify-center">
                    <div class="headline">Post new ones</div>
                  </v-card-title>
                  <v-card-text>
                    Adding new ideas is just as simple as one click.
                    Add new idea, dream or anything else that fits into 1000 symbols :)
                  </v-card-text>
                </v-card>
              </v-flex>
              <v-flex xs12 md4>
                <v-card flat class="transparent">
                  <v-card-text class="text-center">
                    <v-icon x-large class="blue--text text--lighten-2">mdi-wrench</v-icon>
                  </v-card-text>
                  <v-card-title primary-title class="layout justify-center">
                    <div class="headline text-center">Manage it</div>
                  </v-card-title>
                  <v-card-text>
                    Manage your ideas using admin dashboard. You may quickly find your ideas as
                    well as remove them in one click.
                  </v-card-text>
                </v-card>
              </v-flex>
            </v-layout>
          </v-container>
        </v-flex>
      </v-layout>
    </section>
  </v-main>

</template>
