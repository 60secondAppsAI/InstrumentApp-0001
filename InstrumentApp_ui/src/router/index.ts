import { createRouter, createWebHashHistory } from 'vue-router';
import { useAuth } from '../stores/auth';
import SignIn from '../components/SignIn.vue';
import Settings from '../components/Settings.vue';
import MyDashboard from '../components/MyDashboard.vue';

// Dynamic imports for module components
import Instruments from '../components/Instruments.vue';
import InstrumentDetail from '../components/InstrumentDetail.vue';
import InstrumentIdentifiers from '../components/InstrumentIdentifiers.vue';
import InstrumentIdentifierDetail from '../components/InstrumentIdentifierDetail.vue';
import InstrumentAttributes from '../components/InstrumentAttributes.vue';
import InstrumentAttributeDetail from '../components/InstrumentAttributeDetail.vue';
import InstrumentClassifications from '../components/InstrumentClassifications.vue';
import InstrumentClassificationDetail from '../components/InstrumentClassificationDetail.vue';

const routes = [
  {
    path: '/',
    name: 'home',
    redirect: '/dashboard'
  },
  {
    path: '/dashboard',
    name: 'Dashboard',
    component: MyDashboard
  },
  
  // Dynamic module routes
  {
    path: '/instruments',
    name: 'Instruments',
    component: Instruments
  },
  {
    path: '/instrument/:id',
    name: 'InstrumentDetail',
    component: InstrumentDetail,
    props: true
  },
  {
    path: '/instrumentIdentifiers',
    name: 'InstrumentIdentifiers',
    component: InstrumentIdentifiers
  },
  {
    path: '/instrumentIdentifier/:id',
    name: 'InstrumentIdentifierDetail',
    component: InstrumentIdentifierDetail,
    props: true
  },
  {
    path: '/instrumentAttributes',
    name: 'InstrumentAttributes',
    component: InstrumentAttributes
  },
  {
    path: '/instrumentAttribute/:id',
    name: 'InstrumentAttributeDetail',
    component: InstrumentAttributeDetail,
    props: true
  },
  {
    path: '/instrumentClassifications',
    name: 'InstrumentClassifications',
    component: InstrumentClassifications
  },
  {
    path: '/instrumentClassification/:id',
    name: 'InstrumentClassificationDetail',
    component: InstrumentClassificationDetail,
    props: true
  },

  // Core application routes
  {
    path: '/signin',
    name: 'SignIn',
    component: SignIn
  },
//  {
//    path: '/users',
//    name: 'Users',
//    component: Users,
//    meta: { requiresAuth: true }
//  },
  {
    path: '/settings',
    name: 'Settings',
    component: Settings,
    meta: { requiresAuth: true }
  },
  {
    path: '/:pathMatch(.*)*',
    redirect: '/dashboard'
  }
];

const router = createRouter({
  history: createWebHashHistory(import.meta.env.BASE_URL),
  routes,
  scrollBehavior(to, from, savedPosition) {
    return savedPosition || { top: 0 };
  }
});

// Navigation guard for authentication
router.beforeEach((to, from, next) => {
  const auth = useAuth();
  
  if (to.meta.requiresAuth && !auth.isAuthenticated) {
    next({ name: 'SignIn', query: { redirect: to.fullPath } });
  } else if (to.name === 'SignIn' && auth.isAuthenticated) {
    next({ name: 'Dashboard' });
  } else {
    next();
  }
});

export default router;
