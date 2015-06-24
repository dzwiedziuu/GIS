%% Rysowanie wykresów symulowanego wyrza¿ania
% Ten skrypt ma za zadanie narysowaæ wykresy

%% Import biblioteki JAR
% Wymaga wykonania |'mvn assembly:single install'| na obu projektach
%
% * GraphColoringAlgorithm
% * GraphGenerator
%
gcaJarPath = [getenv('USERPROFILE'), '\.m2\repository\GraphColoringAlgorithm\GraphColoringAlgorithm\1.0.0\GraphColoringAlgorithm-1.0.0.jar'];
ggJarPath  = [getenv('USERPROFILE'), '\.m2\repository\GraphGenerator\GraphGenerator\1.0.0\GraphGenerator-1.0.0.jar']; 
ggOutFile  = [getenv('USERPROFILE'), '\Desktop\graph\graph.dat']; 
gcaOutFile = [getenv('USERPROFILE'), '\Desktop\graph\result.dat']; 

ggProbability = 0.8;
ggVertexNum   = 20;

gcaAlfa     = 0.99;
gcaTempInit = 2000;
gcaTempLow  = 20;
gcaKParam   = 20;
gcaBoltzman = 1.0;

ggCmd = ['java -jar ' ggJarPath ' -p ' num2str(ggProbability,'%.2f') ' -v ' int2str(ggVertexNum) ' -of ' ggOutFile ];

% Wywo³anie generatora grafów
system(ggCmd);

num = 10;
iter = 1000;
data = zeros(num,3);


for i = 1:num
    results = zeros(iter,2);
    curInitTemp = gcaTempInit - (i-1) * 200;
    parfor j = 1:iter
        res = [0 0];
        gcaOutFileCur = [gcaOutFile int2str(j)];
        gcaCmd = ['java -jar ' gcaJarPath ' -a ' num2str(gcaAlfa, '%.4f') ' -i ' ggOutFile ' -o ' gcaOutFileCur ' -ti ' num2str(curInitTemp, '%.4f') ' -tm ' num2str(gcaTempLow, '%.4f') ' -k ' int2str(gcaKParam) ' -b ' num2str(gcaBoltzman, '%.4f')];
        system(gcaCmd);
        fileID = fopen(gcaOutFileCur,'r');
        formatSpec = '%f %f %f\n%f';
        out = fscanf(fileID,formatSpec);
        fclose(fileID);
        res(1) = out(4);
        res(2) = out(1);
        results(j,:) = res;
    end
    meanVal = mean(results,1);
    data(i,1) = curInitTemp;
    data(i,2:3) = meanVal;
end

kolTitle = [ 'Graf n = ' int2str(ggVertexNum) ', p = ' num2str(ggProbability,'%.2f') ', k = ' int2str(gcaKParam) ', a = ' num2str(gcaAlfa) ];

figure;
scatter(data(:,1), data(:,2),'r', data(:,1), data(:,3),'b');
title(kolTitle);
legend('Liczba skoków w górê');
xlabel('Temperatura pocz¹tkowa') % x-axis label
ylabel('Liczba skoków w górê') % y-axis label

