export default {
  preset: 'jest-preset-angular',
  setupFilesAfterEnv: ['<rootDir>/setup-jest.ts'],
  globals: {
    'ts-jest': {
      tsconfig: '<rootDir>/tsconfig.spec.json',
      useESM: true
    }
  },
  transform: {
    '^.+\\.(ts|mjs|html|js)$': ['ts-jest', { useESM: true, tsconfig: 'tsconfig.spec.json' }]
  },
  moduleNameMapper: {},
  testEnvironment: 'jsdom',
  moduleFileExtensions: ['ts', 'html', 'js', 'json', 'mjs'],
};
